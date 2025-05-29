import { createApp } from 'vue';
import App from './App.vue';
import { createPinia } from 'pinia';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import router from './router'; // 引入路由实例

const app = createApp(App);
const pinia = createPinia();
app.use(ElementPlus);
app.use(router); // 使用路由
app.use(pinia);
app.mount('#app');
