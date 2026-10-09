import { beforeEach, describe, expect, it, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { NewCreditModalComponent } from './new-credit-modal.component';
import { CreditService } from '../../services/credit.service';
import { ModalService } from '../../services/modal.service';

describe('NewCreditModalComponent', () => {
  let component: NewCreditModalComponent;
  let creditService: { createCredit: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    creditService = { createCredit: vi.fn() };

    TestBed.configureTestingModule({
      providers: [
        { provide: CreditService, useValue: creditService },
        { provide: ModalService, useValue: { close: vi.fn() } },
      ],
    });

    component = TestBed.runInInjectionContext(() => new NewCreditModalComponent());
  });

  it('should reject an amount out of range', () => {
    component.amount = '100';

    component.validate('amount');

    expect(component.errors.amount).toBe('El monto debe estar entre $500 y $50,000');
  });

  it('should reject a term with decimals', () => {
    component.termMonths = '12.5';

    component.validate('termMonths');

    expect(component.errors.termMonths).toBe('Ingresa solo números enteros');
  });

  it('should not create a credit when the form is invalid', async () => {
    await component.create();

    expect(creditService.createCredit).not.toHaveBeenCalled();
  });
});
