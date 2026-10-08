import { Component, OnInit, inject, input } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { IonSkeletonText } from '@ionic/angular';
import { CreditService } from '../../services/credit.service';
import { CreditStatusLabel } from '../../enum/credit.enum';
import { HeaderComponent } from '../../components/header/header.component';

@Component({
  selector: 'cs-credit-detail',
  templateUrl: 'credit-detail.page.html',
  styleUrls: ['credit-detail.page.scss'],
  imports: [CurrencyPipe, RouterLink, IonSkeletonText, HeaderComponent],
})
export class CreditDetailPage implements OnInit {
  public creditService = inject(CreditService);

  public id = input.required<string>();
  public statusLabels = CreditStatusLabel;

  public async ngOnInit(): Promise<void> {
    await this.loadCredit();
  }

  public async loadCredit(): Promise<void> {
    await this.creditService.loadCreditById(this.id());
  }
}
