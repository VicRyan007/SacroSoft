import { defineConfig } from '@playwright/test';

export default defineConfig({
  testDir: '.',
  retries: 0,
  reporter: [['list'], ['html']],
  use: {
    baseURL: 'http://localhost:8080',
  },
});
