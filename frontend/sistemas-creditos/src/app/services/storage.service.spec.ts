import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { StorageService } from './storage.service';

describe('StorageService', () => {
  let service: StorageService;

  beforeEach(() => {
    sessionStorage.clear();
    service = TestBed.inject(StorageService);
  });

  it('should save and read a value on web', async () => {
    await service.set('token', 'abc');

    expect(await service.get('token')).toBe('abc');
  });

  it('should remove a value on web', async () => {
    await service.set('token', 'abc');
    await service.remove('token');

    expect(await service.get('token')).toBeNull();
  });
});
