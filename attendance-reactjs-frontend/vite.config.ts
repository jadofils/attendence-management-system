import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5000,
    host: true,     // listen on all addresses
    strictPort: true, // exit if port is already in use
    open: true      // open browser on server start
  }
})