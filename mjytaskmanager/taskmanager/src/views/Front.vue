<template>
  <div id="app">
    <header class="header">
      <div class="nav-container">
        <el-menu
          mode="horizontal"
          router
          class="nav-menu"
          background-color="#2c3e50"
          text-color="#ffffff"
          active-text-color="#409eff"
          :border-bottom="false"
        >
          <el-menu-item index="/tasks">
            <i class="el-icon-s-order"></i>
            <span>任务列表</span>
          </el-menu-item>
          <el-menu-item index="/todos">
            <i class="el-icon-notebook-1"></i>
            <span>代办事项</span>
          </el-menu-item>
          <el-menu-item index="/profile">
            <i class="el-icon-user-solid"></i>
            <span>个人中心</span>
          </el-menu-item>
        </el-menu>
        <!-- 用户信息区域 -->
        <div class="user-info">
          <span class="user-name">{{ currentUser }}</span>
          <div class="logout-dropdown">
            <el-button
              type="text"
              class="logout-btn"
              @click="logout"
            >
              退出登录
            </el-button>
          </div>
        </div>
      </div>
    </header>
    <main class="main-content">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import { ElMenu, ElMenuItem, ElButton } from 'element-plus';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import {jwtDecode} from "jwt-decode";

const router = useRouter();
const currentUser = ref('');

// 模拟从本地存储获取用户信息
onMounted(() => {
  currentUser.value = jwtDecode(localStorage.getItem('token')).sub || '未知用户';
});

// 退出登录函数
const logout = () => {
  localStorage.removeItem('token');
  router.push('/login');
};
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.header {
  background-color: #2c3e50;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.nav-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
  height: 64px;
}

.nav-menu {
  flex: 1;
}

.el-menu-item {
  font-size: 15px;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.main-content {
  flex: 1;
  padding: 40px;
  background-color: #f5f7fa;
}

.user-info {
  position: relative;
  display: flex;
  align-items: center;
  color: white;
  cursor: pointer;
  padding: 0 10px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.user-info:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.user-name {
  margin-right: 5px;
}

.logout-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  min-width: 120px;
  display: none;
}

.user-info:hover .logout-dropdown {
  display: block;
}

.logout-btn {
  width: 100%;
  text-align: center;
  color: #ff4d4f;
}

.logout-btn:hover {
  background-color: #fff1f0;
}
</style>
