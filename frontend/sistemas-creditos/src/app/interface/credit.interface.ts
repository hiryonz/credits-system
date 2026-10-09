import { CreditStatus } from '../enum/credit.enum';

export interface Credit {
  id: string;
  amount: number;
  termMonths: number;
  applicantDocument: string;
  status: CreditStatus;
  comment?: string;
  createdAt: string;
  updatedAt: string;
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
