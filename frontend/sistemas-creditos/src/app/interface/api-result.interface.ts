export interface ResponseStatus {
  code: string;
  description: string;
}

export interface ApiResult<T> {
  status: ResponseStatus;
  body?: T;
}
