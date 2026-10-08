import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonInput } from '@ionic/angular';
import { ButtonComponent } from '../button/button.component';
import { CreditService } from '../../services/credit.service';
import { ModalService } from '../../services/modal.service';
import { CreditLimit } from '../../enum/credit.enum';

@Component({
  selector: 'app-new-credit-modal',
  templateUrl: 'new-credit-modal.component.html',
  styleUrls: ['new-credit-modal.component.scss'],
  imports: [FormsModule, IonInput, ButtonComponent],
})
export class NewCreditModalComponent {
  public creditService = inject(CreditService);
  private modalService = inject(ModalService);

  public applicantDocument = '';
  public amount = '';
  public termMonths = '';
  public errors = { applicantDocument: '', amount: '', termMonths: '' };

  public validate(field: 'applicantDocument' | 'amount' | 'termMonths'): void {
    this.errors[field] = '';

    if (field === 'applicantDocument' && !this.applicantDocument.trim()) {
      this.errors[field] = 'Ingresa la cédula';
    } else if (field === 'amount' && !this.isInRange(this.amount, CreditLimit.MinAmount, CreditLimit.MaxAmount)) {
      this.errors[field] = 'El monto debe estar entre $500 y $50,000';
    } else if (field === 'termMonths' && !this.isInRange(this.termMonths, CreditLimit.MinTermMonths, CreditLimit.MaxTermMonths)) {
      this.errors[field] = 'El plazo debe estar entre 6 y 60 meses';
    }
  }

  public async create(): Promise<void> {
    this.validate('applicantDocument');
    this.validate('amount');
    this.validate('termMonths');

    if (this.errors.applicantDocument || this.errors.amount || this.errors.termMonths) {
      return;
    }

    const isCreated = await this.creditService.createCredit({
      applicantDocument: this.applicantDocument.trim(),
      amount: Number(this.amount),
      termMonths: Number(this.termMonths),
    });

    if (isCreated) {
      await this.modalService.close();
      await this.creditService.loadCredits();
    }
  }

  public cancel(): void {
    this.modalService.close();
  }

  private isInRange(value: string, min: number, max: number): boolean {
    const number = Number(value);
    return value !== '' && !isNaN(number) && number >= min && number <= max;
  }
}
