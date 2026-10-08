import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { from, switchMap } from 'rxjs';
import { StorageService } from '../services/storage.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const storage = inject(StorageService);

  return from(storage.get('token')).pipe(
    switchMap((token) =>
      token ? next(request.clone({ setHeaders: { Authorization: `Bearer ${token}` } })) : next(request),
    ),
  );
};
