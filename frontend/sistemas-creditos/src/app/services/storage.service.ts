import { Injectable } from '@angular/core';
import { Capacitor } from '@capacitor/core';
import { SecureStorage } from '@aparajita/capacitor-secure-storage';

@Injectable({ providedIn: 'root' })
export class StorageService {
  private isNative = Capacitor.isNativePlatform();

  public async get(key: string): Promise<string | null> {
    if (this.isNative) {
      return SecureStorage.getItem(key);
    }

    return sessionStorage.getItem(key);
  }

  public async set(key: string, value: string): Promise<void> {
    if (this.isNative) {
      return SecureStorage.setItem(key, value);
    }

    sessionStorage.setItem(key, value);
  }

  public async remove(key: string): Promise<void> {
    if (this.isNative) {
      return SecureStorage.removeItem(key);
    }

    sessionStorage.removeItem(key);
  }
}
