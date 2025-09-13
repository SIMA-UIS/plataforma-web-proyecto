import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          0: "#000000",
          10: "#6100FF",
          20: "#C099FF",
          30: "#004F58",
          40: "#1b8892ff",
          50: "#1f9faaff",
          60: "#D5F4F3",
          70: "#6CB6C4",
          80: "#87D2E0",
          90: "#A3EEFC",
          95: "#D1F7FF",
          98: "#EDFCFF",
          100: "#ffffff",
        },
      },
      fontFamily: {
        sans: ["Montserrat", "sans-serif"],
      },
    },
  },
  plugins: [],
};

export default config;
