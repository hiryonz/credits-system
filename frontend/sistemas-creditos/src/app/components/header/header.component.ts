import { Component, OnInit, inject, signal } from '@angular/core';
import { NavController, ViewWillEnter } from '@ionic/angular';
import { AuthService } from '../../services/auth.service';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'cs-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.scss'],
})
export class HeaderComponent implements OnInit {
  private authService = inject(AuthService);
  private storage = inject(StorageService);
  private navController = inject(NavController);

  public username = signal('');

  public async ngOnInit(): Promise<void> {
    this.username.set((await this.storage.get('username')) ?? '');
  }

  public async logout(): Promise<void> {
    await this.authService.logout();
    this.navController.navigateRoot('auth');
  }
}
