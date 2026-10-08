import { CreditStatus } from '../enum/credit-status.enum';

export interface Credit {
  id: string;
  amount: number;
  termMonths: number;
  applicantDocument: string;
  status: CreditStatus;
  comment?: string;
}

export interface NewCreditRequest {
  amount: number;
  termMonths: number;
  applicantDocument: string;
}

export interface CreditStatusChange {
  id: string;
  status: CreditStatus;
  comment: string;
}

export interface CreditListFilter {
  status?: CreditStatus;
}
