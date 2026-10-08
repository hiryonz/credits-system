import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'auth',
    loadComponent: () => import('./pages/auth/auth.page').then((m) => m.AuthPage),
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/dashboard/dashboard.page').then((m) => m.DashboardPage),
  },
  {
    path: 'credit/:id',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/credit-detail/credit-detail.page').then((m) => m.CreditDetailPage),
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'auth',
  },
];
