import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, provideRouter } from '@angular/router';
import { authGuard } from './auth.guard';

const createToken = (expiresInSeconds: number): string => {
  const payload = btoa(JSON.stringify({ exp: Math.floor(Date.now() / 1000) + expiresInSeconds }));
  return `header.${payload}.signature`;
};

const runGuard = () =>
  TestBed.runInInjectionContext(() => authGuard({} as ActivatedRouteSnapshot, {} as RouterStateSnapshot));

describe('authGuard', () => {
  beforeEach(() => {
    sessionStorage.clear();
    TestBed.configureTestingModule({ providers: [provideRouter([])] });
  });

  it('should allow access with a valid session', async () => {
    sessionStorage.setItem('username', 'ana');
    sessionStorage.setItem('token', createToken(60));

    expect(await runGuard()).toBe(true);
  });

  it('should redirect to auth when the token is expired', async () => {
    sessionStorage.setItem('username', 'ana');
    sessionStorage.setItem('token', createToken(-60));

    const result = (await runGuard()) as UrlTree;

    expect(result.toString()).toBe('/auth');
    expect(sessionStorage.getItem('token')).toBeNull();
  });
});
