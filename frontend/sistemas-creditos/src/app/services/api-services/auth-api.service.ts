import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpContext } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SKIP_AUTH_INTERCEPTOR } from '../../interceptors/auth.interceptor';
import { ApiResult } from '../../interface/api-result.interface';
import { AuthenticatedUser, LoginCredentials } from '../../interface/auth.interface';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private http = inject(HttpClient);

  public login(credentials: LoginCredentials): Promise<ApiResult<AuthenticatedUser>> {
    return firstValueFrom(
      this.http.post<ApiResult<AuthenticatedUser>>(`${environment.apiUrl}/auth/login`, credentials),
    );
  }

  public register(credentials: LoginCredentials): Promise<ApiResult<AuthenticatedUser>> {
    return firstValueFrom(
      this.http.post<ApiResult<AuthenticatedUser>>(`${environment.apiUrl}/auth/register`, credentials),
    );
  }

  public refreshToken(token: string): Promise<ApiResult<AuthenticatedUser>> {
    return firstValueFrom(
      this.http.post<ApiResult<AuthenticatedUser>>(`${environment.apiUrl}/auth/refresh-token`, null, {
        headers: { Authorization: `Bearer ${token}` },
        context: new HttpContext().set(SKIP_AUTH_INTERCEPTOR, true),
      }),
    );
  }
}
