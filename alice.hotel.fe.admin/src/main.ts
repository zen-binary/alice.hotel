import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import { i18n } from "@/plugins/i18n";
import vuetify from "@/plugins/vuetify";

import commonPlugin from "@/plugins/common";
const app = createApp(App);
import VueSweetalert2 from "vue-sweetalert2";
import "@/assets/style/_sweetaleart.scss";
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import "@/assets/scss/global.scss";
import globalMethods from "./utils/global.ts";
import "ag-grid-community/styles/ag-grid.css"; // Mandatory CSS required by the Data Grid
import "ag-grid-community/styles/ag-theme-quartz.css"; // Optional Theme applied to the Data Grid

app.use(createPinia());
app.component('QuillEditor', QuillEditor)
app.use(router);
app.use(i18n);
app.use(vuetify);
app.use(commonPlugin);
app.use(VueSweetalert2);

app.config.globalProperties.$global = globalMethods;
app.mount("#app");
