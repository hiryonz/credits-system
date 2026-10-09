import { HttpContextToken, HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { NavController } from '@ionic/angular';
import { catchError, from, switchMap, throwError } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { ToastService } from '../services/toast.service';
import { AuthService } from '../services/auth.service';
import { ApiStatusCode } from '../enum/api-status-code.enum';
import { ModalService } from '../services/modal.service';

/** Peticiones que manejan su propio token (ej. el refresh) y no deben pasar por este interceptor. */
export const SKIP_AUTH_INTERCEPTOR = new HttpContextToken<boolean>(() => false);

/** En login y registro un 401 significa credenciales incorrectas, no sesión expirada. */
const PUBLIC_AUTH_PATHS = ['/auth/login', '/auth/register'];

const isPublicAuthRequest = (url: string): boolean => PUBLIC_AUTH_PATHS.some((path) => url.endsWith(path));

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  if (request.context.get(SKIP_AUTH_INTERCEPTOR)) {
    return next(request);
  }

  const storage = inject(StorageService);
  const toastService = inject(ToastService);
  const navController = inject(NavController);
  const authService = inject(AuthService);
  const modalService = inject(ModalService)

  const getToken = async (): Promise<string | null> => {
    const token = await storage.get('token');
    return token ? await authService.refreshTokenIfNeeded(token) : null;
  };

  return from(getToken()).pipe(
    switchMap((token) =>
      token ? next(request.clone({ setHeaders: { Authorization: `Bearer ${token}` } })) : next(request),
    ),
    catchError((error: HttpErrorResponse) => {
      console.log(error)
      const isSessionExpired = error.status === 401 || error.error?.status?.code === ApiStatusCode.InvalidToken;

      if (isSessionExpired && !isPublicAuthRequest(request.url)) {
        storage.remove('token');
        storage.remove('username');
        modalService.close();
        toastService.error('Tu sesión expiró, vuelve a iniciar sesión');
        navController.navigateRoot('auth');
      }

      return throwError(() => error);
    }),
  );
};
