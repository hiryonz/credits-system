export enum CreditStatus {
  Pending = 'PENDING',
  Approved = 'APPROVED',
  Rejected = 'REJECTED',
}

export enum CreditStatusLabel {
  PENDING = 'Pendiente',
  APPROVED = 'Aprobado',
  REJECTED = 'Rechazado',
}

export enum CreditLimit {
  MinAmount = 500,
  MaxAmount = 50000,
  MinTermMonths = 6,
  MaxTermMonths = 60,
}
