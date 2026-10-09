import { beforeEach, describe, expect, it, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { CreditService } from './credit.service';
import { CreditApiService } from './api-services/credit-api.service';
import { ToastService } from './toast.service';
import { ApiStatusCode } from '../enum/api-status-code.enum';
import { CreditStatus } from '../enum/credit.enum';
import { Credit } from '../interface/credit.interface';

const credit: Credit = {
  id: '1',
  amount: 1500,
  termMonths: 12,
  applicantDocument: '8-888-8888',
  status: CreditStatus.Pending,
  createdAt: '2026-10-08T10:00:00',
  updatedAt: '2026-10-08T10:00:00',
};

describe('CreditService', () => {
  let service: CreditService;
  let creditApi: { getCredits: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    creditApi = { getCredits: vi.fn() };

    TestBed.configureTestingModule({
      providers: [
        { provide: CreditApiService, useValue: creditApi },
        { provide: ToastService, useValue: { success: vi.fn(), error: vi.fn() } },
      ],
    });

    service = TestBed.inject(CreditService);
  });

  it('should load the credits', async () => {
    creditApi.getCredits.mockResolvedValue({ status: { code: ApiStatusCode.CreditSuccess }, body: [credit] });

    await service.loadCredits();

    expect(service.credits()).toEqual([credit]);
    expect(service.isError()).toBe(false);
  });

  it('should show an empty list when there are no credits', async () => {
    creditApi.getCredits.mockRejectedValue(
      new HttpErrorResponse({ status: 404, error: { status: { code: ApiStatusCode.CreditNotFound } } }),
    );

    await service.loadCredits();

    expect(service.credits()).toEqual([]);
    expect(service.isError()).toBe(false);
  });

  it('should flag an error when the service fails', async () => {
    creditApi.getCredits.mockRejectedValue(new HttpErrorResponse({ status: 0 }));

    await service.loadCredits();

    expect(service.isError()).toBe(true);
  });
});
