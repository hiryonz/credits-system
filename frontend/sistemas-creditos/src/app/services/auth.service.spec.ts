import { beforeEach, describe, expect, it, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { NavController } from '@ionic/angular';
import { AuthService } from './auth.service';
import { AuthApiService } from './api-services/auth-api.service';
import { ToastService } from './toast.service';
import { ApiStatusCode } from '../enum/api-status-code.enum';

const createToken = (expiresInSeconds: number): string => {
  const payload = btoa(JSON.stringify({ exp: Math.floor(Date.now() / 1000) + expiresInSeconds }));
  return `header.${payload}.signature`;
};

describe('AuthService.refreshTokenIfNeeded', () => {
  let service: AuthService;
  const authApi = { refreshToken: vi.fn() };

  beforeEach(() => {
    sessionStorage.clear();
    authApi.refreshToken.mockReset();
    TestBed.configureTestingModule({
      providers: [
        { provide: AuthApiService, useValue: authApi },
        { provide: NavController, useValue: {} },
        { provide: ToastService, useValue: {} },
      ],
    });
    service = TestBed.inject(AuthService);
  });

  it('should keep the token when more than 5 minutes remain', async () => {
    const token = createToken(30 * 60);

    expect(await service.refreshTokenIfNeeded(token)).toBe(token);
    expect(authApi.refreshToken).not.toHaveBeenCalled();
  });

  it('should refresh and store the token when 5 minutes or less remain', async () => {
    const token = createToken(4 * 60);
    authApi.refreshToken.mockResolvedValue({
      status: { code: ApiStatusCode.TokenRefreshed },
      body: { token: 'new-token' },
    });

    expect(await service.refreshTokenIfNeeded(token)).toBe('new-token');
    expect(sessionStorage.getItem('token')).toBe('new-token');
  });

  it('should keep the current token when the refresh fails', async () => {
    const token = createToken(4 * 60);
    sessionStorage.setItem('token', token);
    authApi.refreshToken.mockRejectedValue(new Error('network'));

    expect(await service.refreshTokenIfNeeded(token)).toBe(token);
    expect(sessionStorage.getItem('token')).toBe(token);
  });

  it('should share a single refresh between simultaneous requests', async () => {
    const token = createToken(4 * 60);
    authApi.refreshToken.mockResolvedValue({
      status: { code: ApiStatusCode.TokenRefreshed },
      body: { token: 'new-token' },
    });

    const results = await Promise.all([service.refreshTokenIfNeeded(token), service.refreshTokenIfNeeded(token)]);

    expect(results).toEqual(['new-token', 'new-token']);
    expect(authApi.refreshToken).toHaveBeenCalledTimes(1);
  });

  it('should not refresh an expired token', async () => {
    const token = createToken(-60);

    expect(await service.refreshTokenIfNeeded(token)).toBe(token);
    expect(authApi.refreshToken).not.toHaveBeenCalled();
  });
});
