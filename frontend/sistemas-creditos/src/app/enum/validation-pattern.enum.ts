export enum ValidationPattern {
  Username = '^[a-zA-Z0-9._]{4,20}$',
  Password = '^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$',
  Amount = '^\\d+(\\.\\d{1,2})?$',
  TermMonths = '^\\d+$',
}
