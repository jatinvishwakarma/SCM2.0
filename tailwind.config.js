/** @type {import('tailwindcss').Config} */
module.exports = {
    darkMode: 'class',
  content: [
  "./src/main/resources/templates/**/*.html",
  "./src/main/resources/templates/fragments/**/*.html",
  "./src/main/resources/static/**/*.{js,html}",
  "./node_modules/flowbite/**/*.js" // ← add this if Flowbite is installed
],
  theme: {
    extend: {},
  },
  plugins: [],
}
