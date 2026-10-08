export interface LoginCredentials {
  username: string;
  password: string;
}

export interface AuthenticatedUser {
  username?: string;
  token?: string;
}
