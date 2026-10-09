import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';
import { CreditService } from '../../services/credit.service';
import { ModalService } from '../../services/modal.service';
import { CreditStatus, CreditStatusLabel } from '../../enum/credit.enum';

@Component({
  selector: 'cs-change-status-modal',
  templateUrl: 'change-status-modal.component.html',
  styleUrls: ['change-status-modal.component.scss'],
  imports: [FormsModule, ButtonComponent],
})
export class ChangeStatusModalComponent {
  public creditService = inject(CreditService);
  private modalService = inject(ModalService);

  public creditId: string;
  public options = [CreditStatus.Approved, CreditStatus.Rejected];
  public statusLabels = CreditStatusLabel;
  public status: CreditStatus | null = null;
  public comment = '';
  public isOpen = false;
  public errorMessage = '';

  public toggle(): void {
    this.isOpen = !this.isOpen;
  }

  public select(option: CreditStatus): void {
    this.status = option;
    this.isOpen = false;
  }

  public async save(): Promise<void> {
    this.errorMessage = '';

    if (!this.status) {
      this.errorMessage = 'Selecciona un estado';
      return;
    }

    if (!this.comment.trim()) {
      this.errorMessage = 'Escribe un comentario';
      return;
    }

    const isChanged = await this.creditService.changeCreditStatus({
      id: this.creditId,
      status: this.status,
      comment: this.comment.trim(),
    });

    if (isChanged) {
      await this.modalService.close();
      await this.creditService.loadCreditById(this.creditId);
    }
  }

  public cancel(): void {
    this.modalService.close();
  }
}
