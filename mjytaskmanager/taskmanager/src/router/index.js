import { createRouter, createWebHistory } from 'vue-router';
import TaskListView from '../views/TaskListView.vue';
import TodoListView from '../views/TodoListView.vue';
import ProfileView from '../views/ProfileView.vue'; // 引入个人中心组件

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/tasks',
    name: 'Tasks',
    component: TaskListView
  },
  {
    path: '/todos',
    name: 'Todos',
    component: TodoListView
  },
  {
    path: '/profile',
    name: 'Profile',
    component: ProfileView
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
