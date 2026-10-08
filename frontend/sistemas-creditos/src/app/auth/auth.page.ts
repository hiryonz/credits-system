import { Component, signal } from '@angular/core';
import { IonInput, IonButton } from '@ionic/angular';

@Component({
  selector: 'app-auth',
  templateUrl: 'auth.page.html',
  styleUrls: ['auth.page.scss'],
  imports: [IonInput, IonButton],
})
export class AuthPage {
  public isRegister = false;

  toggle() {
    this.isRegister = !this.isRegister;
  }
}
