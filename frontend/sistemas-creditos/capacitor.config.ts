import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'io.ionic.starter',
  appName: 'sistemas-creditos',
  webDir: 'www',
  // La API de prod es http://, la app tiene que servirse por http para poder llamarla
  server: {
    androidScheme: 'http',
    cleartext: true,
  },
  plugins: {
    Keyboard: {
      resizeOnFullScreen: true,
    },
  },
};

export default config;
