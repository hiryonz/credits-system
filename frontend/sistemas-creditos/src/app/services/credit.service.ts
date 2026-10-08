import { Injectable, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { CreditApiService } from './api-services/credit-api.service';
import { ToastService } from './toast.service';
import { ApiResult } from '../interface/api-result.interface';
import { Credit, NewCreditRequest } from '../interface/credit.interface';
import { ApiStatusCode } from '../enum/api-status-code.enum';
import { CreditStatus } from '../enum/credit.enum';

@Injectable({ providedIn: 'root' })
export class CreditService {
  private creditApi = inject(CreditApiService);
  private toastService = inject(ToastService);

  public credits = signal<Credit[]>([]);
  public isLoading = signal(false);
  public isSaving = signal(false);
  public selectedStatus = signal<CreditStatus | null>(null);

  public async loadCredits(): Promise<void> {
    this.isLoading.set(true);

    try {
      const status = this.selectedStatus();
      const result = await this.creditApi.getCredits(status ? { status } : {});
      this.handleListResponse(result);
    } catch (error) {
      this.handleListResponse((error as HttpErrorResponse).error);
    } finally {
      this.isLoading.set(false);
    }
  }

  public async filterByStatus(status: CreditStatus | null): Promise<void> {
    this.selectedStatus.set(status);
    await this.loadCredits();
  }

  public async createCredit(newCredit: NewCreditRequest): Promise<boolean> {
    this.isSaving.set(true);

    try {
      const result = await this.creditApi.createCredit(newCredit);
      return this.handleCreateResponse(result);
    } catch (error) {
      return this.handleCreateResponse((error as HttpErrorResponse).error);
    } finally {
      this.isSaving.set(false);
    }
  }

  private handleListResponse(response: ApiResult<Credit[]>): void {
    switch (response?.status?.code) {
      case ApiStatusCode.CreditSuccess:
        this.credits.set(response.body ?? []);
        break;
      case ApiStatusCode.CreditNotFound:
        this.credits.set([]);
        break;
      default:
        this.toastService.error('No pudimos cargar los créditos, intentalo nuevamente');
    }
  }

  private handleCreateResponse(response: ApiResult<Credit>): boolean {
    switch (response?.status?.code) {
      case ApiStatusCode.CreditCreated:
        this.toastService.success('Crédito creado');
        return true;
      case ApiStatusCode.InvalidAmount:
        this.toastService.error('El monto debe estar entre $500 y $50,000');
        return false;
      case ApiStatusCode.InvalidTerm:
        this.toastService.error('El plazo debe estar entre 6 y 60 meses');
        return false;
      default:
        this.toastService.error('No pudimos crear el crédito, intentalo nuevamente');
        return false;
    }
  }
}
