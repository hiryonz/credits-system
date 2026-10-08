import { Component, OnInit, inject } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { NavController, IonSpinner } from '@ionic/angular';
import { AuthService } from '../../services/auth.service';
import { CreditService } from '../../services/credit.service';
import { StorageService } from '../../services/storage.service';
import { CreditStatus, CreditStatusLabel } from '../../enum/credit.enum';
import { ModalService } from '../../services/modal.service';
import { NewCreditModalComponent } from '../../components/new-credit-modal/new-credit-modal.component';
import { Credit } from '../../interface/credit.interface';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'dashboard.page.html',
  styleUrls: ['dashboard.page.scss'],
  imports: [CurrencyPipe, IonSpinner],
})
export class DashboardPage implements OnInit {
  public creditService = inject(CreditService);
  private authService = inject(AuthService);
  private storage = inject(StorageService);
  private navController = inject(NavController);
  private modalService = inject(ModalService);

  public username = '';
  public selectedStatus: CreditStatus | null = null;
  public statuses = Object.values(CreditStatus);
  public statusLabels = CreditStatusLabel;

  public async ngOnInit(): Promise<void> {
    this.username = (await this.storage.get('username')) ?? '';
    await this.creditService.loadCredits();
  }

  public getVisibleCredits(): Credit[] {
    const credits = this.creditService.credits();
    return this.selectedStatus ? credits.filter((credit) => credit.status === this.selectedStatus) : credits;
  }

  public countByStatus(status: CreditStatus): number {
    return this.creditService.credits().filter((credit) => credit.status === status).length;
  }

  public getVisibleTotal(): number {
    return this.getVisibleCredits().reduce((total, credit) => total + credit.amount, 0);
  }

  public selectStatus(status: CreditStatus | null): void {
    this.selectedStatus = status;
  }

  public openNewCreditModal(): void {
    this.modalService.presentDialog(NewCreditModalComponent);
  }

  public async logout(): Promise<void> {
    await this.authService.logout();
    this.navController.navigateRoot('auth');
  }
}
