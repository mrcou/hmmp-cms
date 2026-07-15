import { defineConfig } from '@vben/vite-config';

export default defineConfig(async () => {
  return {
    application: {},
    vite: {
      server: {
        proxy: {
          '/dev-api': {
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/dev-api/, ''),
            target: 'http://localhost:8080',
            ws: true,
          },
          '/profile': {
            changeOrigin: true,
            target: 'http://localhost:8080',
          },
        },
      },
    },
  };
});
