import { Component, OnInit, inject, input } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { IonSkeletonText } from '@ionic/angular';
import { CreditService } from '../../services/credit.service';
import { CreditStatus, CreditStatusLabel } from '../../enum/credit.enum';
import { HeaderComponent } from '../../components/header/header.component';
import { ChangeStatusModalComponent } from '../../components/change-status-modal/change-status-modal.component';
import { ModalService } from '../../services/modal.service';

@Component({
  selector: 'cs-credit-detail',
  templateUrl: 'credit-detail.page.html',
  styleUrls: ['credit-detail.page.scss'],
  imports: [CurrencyPipe, DatePipe, RouterLink, IonSkeletonText, HeaderComponent],
})
export class CreditDetailPage implements OnInit {
  public creditService = inject(CreditService);
  private modalService = inject(ModalService);

  public id = input.required<string>();
  public statusLabels = CreditStatusLabel;
  public pendingStatus = CreditStatus.Pending;

  public async ngOnInit(): Promise<void> {
    await this.loadCredit();
  }

  public async loadCredit(): Promise<void> {
    await this.creditService.loadCreditById(this.id());
  }

  public openChangeStatusModal(): void {
    this.modalService.presentDialog(ChangeStatusModalComponent, { creditId: this.id() });
  }
}
