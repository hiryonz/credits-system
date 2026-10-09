import { Component, input } from '@angular/core';
import { IonButton, IonSpinner } from '@ionic/angular';

@Component({
  selector: 'cs-button',
  templateUrl: 'button.component.html',
  imports: [IonButton, IonSpinner],
})
export class ButtonComponent {
  public text = input.required<string>();
  public type = input<'button' | 'submit'>('submit');
  public isLoading = input(false);
}
