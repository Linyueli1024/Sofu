import { createSSRApp } from 'vue'
import pinia from './stores'
import Vue from 'vue'

export function createApp() {
  const app = createSSRApp(App)

  app.use(pinia)
// app.use(Button);
  return {
    app,
  }
}
