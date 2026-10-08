import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { NavController } from '@ionic/angular';
import { catchError, from, switchMap, throwError } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { ToastService } from '../services/toast.service';
import { ApiStatusCode } from '../enum/api-status-code.enum';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const storage = inject(StorageService);
  const toastService = inject(ToastService);
  const navController = inject(NavController);

  return from(storage.get('token')).pipe(
    switchMap((token) =>
      token ? next(request.clone({ setHeaders: { Authorization: `Bearer ${token}` } })) : next(request),
    ),
    catchError((error: HttpErrorResponse) => {
      console.log(error)
      if (error.status === 401 || error.error?.status?.code === ApiStatusCode.InvalidToken) {
        storage.remove('token');
        storage.remove('username');
        toastService.error('Tu sesión expiró, vuelve a iniciar sesión');
        navController.navigateRoot('auth');
      }

      return throwError(() => error);
    }),
  );
};
