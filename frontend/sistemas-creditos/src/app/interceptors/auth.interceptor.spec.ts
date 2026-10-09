import { beforeEach, describe, expect, it, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { NavController } from '@ionic/angular';
import { firstValueFrom } from 'rxjs';
import { authInterceptor } from './auth.interceptor';
import { ToastService } from '../services/toast.service';

describe('authInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;
  const navController = { navigateRoot: vi.fn() };
  const toastService = { error: vi.fn() };

  beforeEach(() => {
    sessionStorage.clear();
    navController.navigateRoot.mockReset();
    toastService.error.mockReset();
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([authInterceptor])),
        provideHttpClientTesting(),
        { provide: NavController, useValue: navController },
        { provide: ToastService, useValue: toastService },
      ],
    });
    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  const failWith401 = async (url: string) => {
    const request = firstValueFrom(http.post(url, {})).catch((): void => undefined);
    await vi.waitFor(() => httpMock.expectOne(url).flush({}, { status: 401, statusText: 'Unauthorized' }));
    await request;
  };

  it('should not treat a 401 on login as an expired session', async () => {
    sessionStorage.setItem('token', 'old-token');

    await failWith401('http://api/auth/login');

    expect(navController.navigateRoot).not.toHaveBeenCalled();
    expect(sessionStorage.getItem('token')).toBe('old-token');
  });

  it('should close the session on a 401 from a protected endpoint', async () => {
    sessionStorage.setItem('token', 'old-token');

    await failWith401('http://api/credit-requests/get-credits');

    expect(navController.navigateRoot).toHaveBeenCalledWith('auth');
    expect(toastService.error).toHaveBeenCalled();
  });
});
