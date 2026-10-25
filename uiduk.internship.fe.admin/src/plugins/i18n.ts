import { createI18n } from "vue-i18n";

import VI from "@/locales/vi/module.json";
import KO from "@/locales/ko/module.json";
import EN from "@/locales/en/module.json";

// Type-define 'en-US' as the master schema for the resource
type MessageSchema = typeof VI;

export const i18n = createI18n<[MessageSchema], "en" | "vi" | "ko">({
  legacy: false,
  locale: "ko",
  messages: {
    vi: VI,
    ko: KO,
    en: EN,
  },
  fallbackLocale: "vi",
});
