import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResult } from '../../interface/api-result.interface';
import { Credit, CreditListFilter, CreditStatusChange, NewCreditRequest } from '../../interface/credit.interface';

@Injectable({ providedIn: 'root' })
export class CreditApiService {
  private http = inject(HttpClient);

  public createCredit(newCredit: NewCreditRequest): Promise<ApiResult<Credit>> {
    return firstValueFrom(
      this.http.post<ApiResult<Credit>>(`${environment.apiUrl}/credit-requests/create-credits`, newCredit),
    );
  }

  public getCredits(filter: CreditListFilter = {}): Promise<ApiResult<Credit[]>> {
    return firstValueFrom(
      this.http.post<ApiResult<Credit[]>>(`${environment.apiUrl}/credit-requests/get-credits`, filter),
    );
  }

  public getCreditById(id: string): Promise<ApiResult<Credit>> {
    return firstValueFrom(this.http.get<ApiResult<Credit>>(`${environment.apiUrl}/credit-requests/${id}`));
  }

  public changeCreditStatus(statusChange: CreditStatusChange): Promise<ApiResult<Credit>> {
    return firstValueFrom(
      this.http.post<ApiResult<Credit>>(`${environment.apiUrl}/credit-requests/update-credit-status`, statusChange),
    );
  }
}
