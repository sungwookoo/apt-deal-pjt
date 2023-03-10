import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

// Import Bootstrap and BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// vue-moment 사용
import VueMoment from "vue-moment";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

// vue-moment 사용
Vue.use(VueMoment);

// 천 자리 표시 필터
Vue.filter("filterPrice", (value) => {
  if (!value) return value;
  return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
});

// 문자열 줄바꿈 처리 필터
Vue.filter("filterEnterToBr", (value) => {
  if (!value) return value;
  // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
  return value.replace(/(?:\r\n|\r|\n)/g, "<br />");
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
