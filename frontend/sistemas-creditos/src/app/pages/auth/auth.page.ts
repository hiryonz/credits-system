import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonInput, IonInputPasswordToggle, ViewWillEnter } from '@ionic/angular';
import { AuthService } from '../../services/auth.service';
import { ButtonComponent } from '../../components/button/button.component';
import { ValidationPattern } from '../../enum/validation-pattern.enum';

@Component({
  selector: 'cs-auth',
  templateUrl: 'auth.page.html',
  styleUrls: ['auth.page.scss'],
  imports: [FormsModule, IonInput, IonInputPasswordToggle, ButtonComponent],
})
export class AuthPage implements ViewWillEnter {

  public authService = inject(AuthService);

  public isRegister = false;
  public username = signal('');
  public password = signal('');
  public confirmPassword = '';
  public errors = { username: '', password: '' };

  ionViewWillEnter(): void {
    console.log('reset called')
    this.resetInfo();
  }

  public validate(field: 'username' | 'password' | 'confirmPassword'): void {
    if (field === 'username') {
      this.errors.username = '';

      if (!this.username()) {
        this.errors.username = 'Ingresa tu usuario';
      } else if (this.isRegister && !new RegExp(ValidationPattern.Username).test(this.username())) {
        this.errors.username = 'El usuario debe tener de 4 a 20 caracteres';
      }

      return;
    }

    this.errors.password = '';

    if (!this.password()) {
      this.errors.password = 'Ingresa tu contraseña';
    } else if (this.isRegister && !new RegExp(ValidationPattern.Password).test(this.password())) {
      this.errors.password = 'Usa mínimo 8 caracteres, con al menos una mayúscula y un carácter especial';
    } else if (field === 'confirmPassword' && this.password() !== this.confirmPassword) {
      this.errors.password = 'Las contraseñas no coinciden';
    }
  }

  public toggle(): void {
    this.isRegister = !this.isRegister;
    this.resetInfo();
  }

  public async login(): Promise<void> {
    if (this.errors.username || this.errors.password) {
      return;
    }

    await this.authService.login({ username: this.username(), password: this.password() });
  }

  public async register(): Promise<void> {
    if (this.errors.username || this.errors.password) {
      return;
    }

    await this.authService.register({ username: this.username(), password: this.password() });
  }

  private resetInfo(): void {
    this.username.set('');
    this.password.set('');
    this.confirmPassword = '';
    this.errors = { username: '', password: '' };
  }
}
