import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment';
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
}
