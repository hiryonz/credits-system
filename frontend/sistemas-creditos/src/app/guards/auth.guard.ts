import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { getTokenExpiration } from '../utils/jwt.util';

export const authGuard: CanActivateFn = async () => {
  const storage = inject(StorageService);
  const router = inject(Router);

  const username = await storage.get('username');
  const token = await storage.get('token');

  if (username && isTokenValid(token)) {
    return true;
  }

  await storage.remove('token');
  await storage.remove('username');
  return router.parseUrl('/auth');
};

function isTokenValid(token: string | null): boolean {
  const expiration = getTokenExpiration(token);
  return expiration !== null && expiration > Date.now();
}
