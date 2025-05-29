<template>
  <div class="login-container">
    <div class="login-wrapper">
      <el-card class="login-card animated fadeIn">
        <div class="logo">
          <img src="https://via.placeholder.com/100" alt="Logo" class="logo-img" />
        </div>
        <h2 class="login-title">任务管理系统登录</h2>
        <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
              class="input-field"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
              class="input-field"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="handleLogin"
              style="width: 100%"
              :loading="isLoading"
              class="login-button"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import request from '@/utils/request'; // 引入封装的 axios 实例
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

// 定义登录表单数据
const loginForm = ref({
  username: '',
  password: ''
});

// 定义加载状态
const isLoading = ref(false);

// 定义表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
};

// 定义表单引用
const loginFormRef = ref(null);
const router = useRouter();

// 处理登录逻辑
const handleLogin = async () => {
  if (!loginFormRef.value) return;
  isLoading.value = true;
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 发送登录请求，使用封装的 request 实例
        const response = await request.post('/api/login', loginForm.value);
        if (response.code = '200') {
          const token = response.data.token;
          // 存储 JWT token
          localStorage.setItem('token', token);
          // 登录成功，跳转到任务列表页面
          router.push('/tasks');
        } else {
          ElMessage.error(response.message || '登录失败，请检查用户名和密码');
        }
      } catch (error) {
        console.error('登录请求出错:', error);
      } finally {
        isLoading.value = false;
      }
    } else {
      console.log('表单验证失败');
      isLoading.value = false;
      return false;
    }
  });
};
</script>

<style scoped>
/* 样式部分保持不变 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  animation: gradient 15s ease infinite;
  background-size: 400% 400%;
}

@keyframes gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100 50%;
  }
  100% {
    background-position: 0 50%;
  }
}

.login-wrapper {
  width: 100%;
  max-width: 420px;
  padding: 20px;
}

.login-card {
  border-radius: 16px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.95);
}

.animated {
  animation-duration: 1s;
  animation-fill-mode: both;
}

.fadeIn {
  animation-name: fadeIn;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.logo {
  text-align: center;
  margin-bottom: 30px;
}

.logo-img {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.logo-img:hover {
  transform: scale(1.05);
}

.login-title {
  text-align: center;
  margin-bottom: 40px;
  color: #2c3e50;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.el-form-item {
  margin-bottom: 25px;
}

.input-field {
  border-radius: 10px;
  height: 48px;
  transition: box-shadow 0.3s ease;
}

.input-field:hover,
.input-field:focus {
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.3);
}

/* 新增输入框文字对齐样式 */
.input-field .el-input__inner {
  display: flex;
  align-items: center;
  line-height: normal; /* 重置行高 */
}

.login-button {
  border-radius: 10px;
  font-size: 18px;
  height: 52px;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}
</style>

<!--<template>-->
<!--  <div class="login-container">-->
<!--    <div class="login-wrapper">-->
<!--      <el-card class="login-card animated fadeIn">-->
<!--        <div class="logo">-->
<!--          <img src="https://via.placeholder.com/100" alt="Logo" class="logo-img" />-->
<!--        </div>-->
<!--        <h2 class="login-title">任务管理系统登录</h2>-->
<!--        <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">-->
<!--          <el-form-item label="用户名" prop="username">-->
<!--            <el-input-->
<!--              v-model="loginForm.username"-->
<!--              placeholder="请输入用户名"-->
<!--              prefix-icon="el-icon-user"-->
<!--              class="input-field"-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="密码" prop="password">-->
<!--            <el-input-->
<!--              v-model="loginForm.password"-->
<!--              type="password"-->
<!--              placeholder="请输入密码"-->
<!--              prefix-icon="el-icon-lock"-->
<!--              class="input-field"-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="角色" prop="role">-->
<!--            <el-select v-model="loginForm.role" placeholder="请选择角色" class="input-field">-->
<!--              <el-option label="管理员" value="admin"></el-option>-->
<!--              <el-option label="普通用户" value="user"></el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--          <el-form-item class="remember-me">-->
<!--            <el-checkbox v-model="loginForm.remember">记住密码</el-checkbox>-->
<!--          </el-form-item>-->
<!--          <el-form-item>-->
<!--            <el-button-->
<!--              type="primary"-->
<!--              @click="handleLogin"-->
<!--              style="width: 100%"-->
<!--              :loading="isLoading"-->
<!--              class="login-button"-->
<!--            >-->
<!--              登录-->
<!--            </el-button>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--      </el-card>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { ref, onMounted } from 'vue';-->
<!--import request from '@/utils/request'; // 引入封装的 axios 实例-->
<!--import { useRouter } from 'vue-router';-->
<!--import { ElMessage } from 'element-plus';-->

<!--// 定义登录表单数据-->
<!--const loginForm = ref({-->
<!--  username: '',-->
<!--  password: '',-->
<!--  role: '',-->
<!--  remember: false-->
<!--});-->

<!--// 定义加载状态-->
<!--const isLoading = ref(false);-->

<!--// 定义表单验证规则-->
<!--const rules = {-->
<!--  username: [-->
<!--    { required: true, message: '请输入用户名', trigger: 'blur' }-->
<!--  ],-->
<!--  password: [-->
<!--    { required: true, message: '请输入密码', trigger: 'blur' }-->
<!--  ],-->
<!--  role: [-->
<!--    { required: true, message: '请选择角色', trigger: 'change' }-->
<!--  ]-->
<!--};-->

<!--// 定义表单引用-->
<!--const loginFormRef = ref(null);-->
<!--const router = useRouter();-->

<!--// 页面加载时检查本地存储-->
<!--onMounted(() => {-->
<!--  const remember = localStorage.getItem('remember');-->
<!--  if (remember === 'true') {-->
<!--    loginForm.value.username = localStorage.getItem('username') || '';-->
<!--    loginForm.value.password = localStorage.getItem('password') || '';-->
<!--    loginForm.value.role = localStorage.getItem('role') || '';-->
<!--    loginForm.value.remember = true;-->
<!--  }-->
<!--});-->

<!--// 处理登录逻辑-->
<!--const handleLogin = async () => {-->
<!--  if (!loginFormRef.value) return;-->
<!--  isLoading.value = true;-->
<!--  await loginFormRef.value.validate(async (valid) => {-->
<!--    if (valid) {-->
<!--      try {-->
<!--        // 发送登录请求，使用封装的 request 实例-->
<!--        const response = await request.post('/api/login', loginForm.value);-->
<!--        if (response.success) {-->
<!--          const token = response.data.token;-->
<!--          if (loginForm.value.remember) {-->
<!--            localStorage.setItem('remember', 'true');-->
<!--            localStorage.setItem('username', loginForm.value.username);-->
<!--            localStorage.setItem('password', loginForm.value.password);-->
<!--            localStorage.setItem('role', loginForm.value.role);-->
<!--          } else {-->
<!--            localStorage.removeItem('remember');-->
<!--            localStorage.removeItem('username');-->
<!--            localStorage.removeItem('password');-->
<!--            localStorage.removeItem('role');-->
<!--          }-->
<!--          // 存储 JWT token-->
<!--          localStorage.setItem('token', token);-->
<!--          // 登录成功，跳转到任务列表页面-->
<!--          router.push('/tasks');-->
<!--        } else {-->
<!--          ElMessage.error(response.message || '登录失败，请检查用户名和密码');-->
<!--        }-->
<!--      } catch (error) {-->
<!--        console.error('登录请求出错:', error);-->
<!--      } finally {-->
<!--        isLoading.value = false;-->
<!--      }-->
<!--    } else {-->
<!--      console.log('表单验证失败');-->
<!--      isLoading.value = false;-->
<!--      return false;-->
<!--    }-->
<!--  });-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--/* 样式部分保持不变 */-->
<!--.login-container {-->
<!--  display: flex;-->
<!--  justify-content: center;-->
<!--  align-items: center;-->
<!--  height: 100vh;-->
<!--  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);-->
<!--  animation: gradient 15s ease infinite;-->
<!--  background-size: 400% 400%;-->
<!--}-->

<!--@keyframes gradient {-->
<!--  0% {-->
<!--    background-position: 0% 50%;-->
<!--  }-->
<!--  50% {-->
<!--    background-position: 100% 50%;-->
<!--  }-->
<!--  100% {-->
<!--    background-position: 0% 50%;-->
<!--  }-->
<!--}-->

<!--.login-wrapper {-->
<!--  width: 100%;-->
<!--  max-width: 420px;-->
<!--  padding: 20px;-->
<!--}-->

<!--.login-card {-->
<!--  border-radius: 16px;-->
<!--  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);-->
<!--  padding: 40px;-->
<!--  background-color: rgba(255, 255, 255, 0.95);-->
<!--}-->

<!--.animated {-->
<!--  animation-duration: 1s;-->
<!--  animation-fill-mode: both;-->
<!--}-->

<!--.fadeIn {-->
<!--  animation-name: fadeIn;-->
<!--}-->

<!--@keyframes fadeIn {-->
<!--  from {-->
<!--    opacity: 0;-->
<!--    transform: translateY(-20px);-->
<!--  }-->
<!--  to {-->
<!--    opacity: 1;-->
<!--    transform: translateY(0);-->
<!--  }-->
<!--}-->

<!--.logo {-->
<!--  text-align: center;-->
<!--  margin-bottom: 30px;-->
<!--}-->

<!--.logo-img {-->
<!--  width: 120px;-->
<!--  height: 120px;-->
<!--  border-radius: 50%;-->
<!--  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);-->
<!--  transition: transform 0.3s ease;-->
<!--}-->

<!--.logo-img:hover {-->
<!--  transform: scale(1.05);-->
<!--}-->

<!--.login-title {-->
<!--  text-align: center;-->
<!--  margin-bottom: 40px;-->
<!--  color: #2c3e50;-->
<!--  font-size: 28px;-->
<!--  font-weight: 700;-->
<!--  letter-spacing: 0.5px;-->
<!--}-->

<!--.el-form-item {-->
<!--  margin-bottom: 25px;-->
<!--}-->

<!--.input-field {-->
<!--  border-radius: 10px;-->
<!--  height: 48px;-->
<!--  transition: box-shadow 0.3s ease;-->
<!--}-->

<!--.input-field:hover,-->
<!--.input-field:focus {-->
<!--  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.3);-->
<!--}-->

<!--/* 新增输入框文字对齐样式 */-->
<!--.input-field .el-input__inner {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  line-height: normal; /* 重置行高 */-->
<!--}-->

<!--.remember-me {-->
<!--  margin-bottom: 30px;-->
<!--  color: #555;-->
<!--}-->

<!--.login-button {-->
<!--  border-radius: 10px;-->
<!--  font-size: 18px;-->
<!--  height: 52px;-->
<!--  transition: all 0.3s ease;-->
<!--}-->

<!--.login-button:hover {-->
<!--  transform: translateY(-2px);-->
<!--  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);-->
<!--}-->

<!--.el-select {-->
<!--  border-radius: 10px;-->
<!--}-->
<!--/* 新增选择器让选择框文字对齐 */-->
<!--.el-select .el-input__inner {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  line-height: normal;-->
<!--}-->
<!--</style>-->
