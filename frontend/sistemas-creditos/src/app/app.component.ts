import { Component, DestroyRef, inject } from '@angular/core';
import { IonApp, IonRouterOutlet, Platform } from '@ionic/angular';

@Component({
  selector: 'cs-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, IonRouterOutlet],
})
export class AppComponent {

  private destroyRef = inject(DestroyRef);
  private platform = inject(Platform);

  constructor() {
    // Android (web y app) y la app de iOS ya achican la pantalla solos; solo Safari en iOS necesita ayuda
    if (this.platform.is('ios') && !this.platform.is('hybrid')) {
      this.fitToVisibleViewport();
    }
  }

  // Ajusta la altura de la app al área visible cuando se abre el teclado
  private fitToVisibleViewport(): void {
    const viewport = window.visualViewport;
    if (!viewport) return;

    let frame = 0;
    let scrollTimeout: ReturnType<typeof setTimeout>;

    const update = () => {
      cancelAnimationFrame(frame);
      frame = requestAnimationFrame(() => {
        document.documentElement.style.setProperty('--app-height', `${viewport.height}px`);
      });

      // Esperar a que termine la animación del teclado para no saltar en cada frame
      clearTimeout(scrollTimeout);
      scrollTimeout = setTimeout(() => {
        if (document.activeElement instanceof HTMLElement) {
          document.activeElement.scrollIntoView({ block: 'nearest' });
        }
      }, 150);
    };

    update();
    viewport.addEventListener('resize', update);
    this.destroyRef.onDestroy(() => {
      viewport.removeEventListener('resize', update);
      cancelAnimationFrame(frame);
      clearTimeout(scrollTimeout);
    });
  }
}
