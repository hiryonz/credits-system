import { Component, OnInit, inject } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { IonSkeletonText } from '@ionic/angular';
import { CreditService } from '../../services/credit.service';
import { CreditStatus, CreditStatusLabel } from '../../enum/credit.enum';
import { ModalService } from '../../services/modal.service';
import { NewCreditModalComponent } from '../../components/new-credit-modal/new-credit-modal.component';
import { HeaderComponent } from '../../components/header/header.component';

@Component({
  selector: 'cs-dashboard',
  templateUrl: 'dashboard.page.html',
  styleUrls: ['dashboard.page.scss'],
  imports: [CurrencyPipe, RouterLink, IonSkeletonText, HeaderComponent],
})
export class DashboardPage implements OnInit {
  public creditService = inject(CreditService);
  private modalService = inject(ModalService);

  public statuses = Object.values(CreditStatus);
  public statusLabels = CreditStatusLabel;
  public skeletonRows = [1, 2, 3, 4, 5];

  public async ngOnInit(): Promise<void> {
    await this.creditService.loadCredits();
  }

  public selectStatus(status: CreditStatus | null): void {
    this.creditService.filterByStatus(status);
  }

  public openNewCreditModal(): void {
    this.modalService.presentDialog(NewCreditModalComponent);
  }
}
