/** Fecha de expiración del JWT en milisegundos, o null si el token no se puede leer. */
export function getTokenExpiration(token: string | null): number | null {
  try {
    const payload = JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
    return typeof payload.exp === 'number' ? payload.exp * 1000 : null;
  } catch {
    return null;
  }
}
