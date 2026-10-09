import { Injectable, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthApiService } from './api-services/auth-api.service';
import { StorageService } from './storage.service';
import { AuthenticatedUser, LoginCredentials } from '../interface/auth.interface';
import { NavController } from '@ionic/angular';
import { ApiStatusCode } from '../enum/api-status-code.enum';
import { ApiResult } from '../interface/api-result.interface';
import { ToastService } from './toast.service';
import { getTokenExpiration } from '../utils/jwt.util';

const TOKEN_REFRESH_THRESHOLD_MS = 5 * 60 * 1000;

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authApi = inject(AuthApiService);
  private storage = inject(StorageService);
  private navControlller = inject(NavController);
  private toastService = inject(ToastService);

  public isLoading = signal(false);
  private credentials: LoginCredentials;
  private refreshInProgress: Promise<string> | null = null;

  public async login(credentials: LoginCredentials): Promise<void> {
    this.isLoading.set(true);
    await this.storage.remove('username');
    await this.storage.remove('token');
    try {
      const result = await this.authApi.login(credentials);
      await this.handleResponse(result);
    } catch (error) {
      await this.handleResponse((error as HttpErrorResponse).error);
    } finally {
      this.isLoading.set(false);
    }
  }

  public async register(credentials: LoginCredentials): Promise<void> {
    this.isLoading.set(true);
    this.credentials = credentials;

    try {
      const result = await this.authApi.register(credentials);
      await this.handleResponse(result);
    } catch (error) {
      console.log(error);
      await this.handleResponse((error as HttpErrorResponse).error);
      return;
    } finally {
      this.isLoading.set(false);
    }
  }

  /**
   * Si al token le quedan 5 minutos o menos, pide uno nuevo y lo guarda.
   * Nunca falla: si la renovación no funciona devuelve el token actual.
   */
  public refreshTokenIfNeeded(token: string): Promise<string> {
    const expiration = getTokenExpiration(token);
    const remaining = expiration === null ? 0 : expiration - Date.now();

    if (remaining <= 0 || remaining > TOKEN_REFRESH_THRESHOLD_MS) {
      return Promise.resolve(token);
    }

    // Varias peticiones simultáneas comparten la misma renovación
    this.refreshInProgress ??= this.refreshToken(token).finally(() => (this.refreshInProgress = null));
    return this.refreshInProgress;
  }

  private async refreshToken(token: string): Promise<string> {
    try {
      const result = await this.authApi.refreshToken(token);
      const newToken = result?.body?.token;

      if (result?.status?.code !== ApiStatusCode.TokenRefreshed || !newToken) {
        return token;
      }

      await this.storage.set('token', newToken);
      return newToken;
    } catch {
      return token;
    }
  }

  public async logout(): Promise<void> {
    await this.storage.remove('token');
    await this.storage.remove('username');
  }

  private async handleResponse(response: ApiResult<AuthenticatedUser>): Promise<void> {
    switch (response?.status?.code) {
      case ApiStatusCode.LoginSuccess:
        if (!response?.body?.token || !response?.body?.username) {
            this.toastService.error('Algo no salio como esperabamos, intentalo nuevamente');
            return;
        }

        await this.storage.set('token', response.body.token);
        await this.storage.set('username', response.body.username);
        this.navControlller.navigateForward('dashboard');
        break;
      case ApiStatusCode.UserCreated:
        await this.login(this.credentials);
        break;
      case ApiStatusCode.InvalidCredentials:
        this.toastService.error('Usuario o contraseña incorrectos');
        break;
      case ApiStatusCode.UserAlreadyExists:
        this.toastService.error('El usuario ya existe');
        break;
      default:
        this.toastService.error('Algo no salio como esperabamos, intentalo nuevamente');
    }
  }
}
