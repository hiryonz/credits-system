import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonInput } from '@ionic/angular';
import { AuthService } from '../../services/auth.service';
import { ButtonComponent } from '../../components/button/button.component';
import { ValidationPattern } from '../../enum/validation-pattern.enum';

@Component({
  selector: 'app-auth',
  templateUrl: 'auth.page.html',
  styleUrls: ['auth.page.scss'],
  imports: [FormsModule, IonInput, ButtonComponent],
})
export class AuthPage {
  public authService = inject(AuthService);

  public isRegister = false;
  public username = '';
  public password = '';
  public confirmPassword = '';
  public errors = { username: '', password: '' };

  public validate(field: 'username' | 'password' | 'confirmPassword'): void {
    if (field === 'username') {
      this.errors.username = '';

      if (!this.username) {
        this.errors.username = 'Ingresa tu usuario';
      } else if (!new RegExp(ValidationPattern.Username).test(this.username)) {
        this.errors.username = 'El usuario debe tener de 4 a 20 caracteres';
      }

      return;
    }

    this.errors.password = '';

    if (!this.password) {
      this.errors.password = 'Ingresa tu contraseña';
    } else if (!new RegExp(ValidationPattern.Password).test(this.password)) {
      this.errors.password = 'Usa mínimo 8 caracteres, con al menos una mayúscula y un carácter especial';
    } else if (field === 'confirmPassword' && this.password !== this.confirmPassword) {
      this.errors.password = 'Las contraseñas no coinciden';
    }
  }

  public toggle(): void {
    this.isRegister = !this.isRegister;
    this.username = '';
    this.password = '';
    this.confirmPassword = '';
    this.errors = { username: '', password: '' };
  }

  public async login(): Promise<void> {
    if (this.errors.username || this.errors.password) {
      return;
    }

    await this.authService.login({ username: this.username, password: this.password });
  }

  public async register(): Promise<void> {
    if (this.errors.username || this.errors.password) {
      return;
    }

    await this.authService.register({ username: this.username, password: this.password });
  }
}
