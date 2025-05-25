<template>
  <div id="app">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/tasks">任务列表</el-menu-item>
      <el-menu-item index="/todos">代办事项</el-menu-item>
      <!-- 添加个人中心菜单项 -->
      <el-menu-item index="/profile">个人中心</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script setup>
import { ElMenu, ElMenuItem } from 'element-plus';
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
