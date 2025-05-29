<template>
  <div class="task-list-container">
    <el-card class="task-list-card">
      <template #header>
        <div class="task-list-header">
          <h2>任务列表</h2>
          <!-- 添加搜索框 -->
          <el-input
            v-model="searchQuery"
            placeholder="搜索任务名称"
            prefix-icon="el-icon-search"
            style="width: 200px;"
            @input="searchTasks"
          ></el-input>
          <el-button type="primary" @click="showAddTaskDialog = true">添加任务</el-button>
        </div>
      </template>
      <!-- 添加加载状态 -->
      <el-table :data="tasks.list" style="width: 100%" :loading="isLoading">
        <el-table-column prop="name" label="任务名称">
          <template #default="{ row }">
            <span
              :style="{ textDecoration: row.completed ? 'line-through' : 'none', cursor: 'pointer' }"
              @click="showTaskDetail(row)"
            >
              {{ row.name }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="{ row }">
            <!-- 格式化时间 -->
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="completed" label="状态" width="100">
          <template #default="{ row }">
            <el-tag
              :type="row.completed ? 'success' : 'warning'"
              @click="showTaskDetail(row)"
              style="cursor: pointer"
            >
              {{ row.completed ? '已完成' : '未完成' }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 添加任务进度列 -->
        <el-table-column prop="progress" label="进度" width="180">
          <template #default="{ row }">
            <el-progress :percentage="row.progress" :format="formatProgress" />
          </template>
        </el-table-column>
        <!-- 添加指派人列 -->
        <el-table-column prop="username" label="指派人" width="120">
          <template #default="{ row }">
            <span>{{ row.assigneeName || '未指派' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="urgency" label="缓急程度" width="120">
          <template #default="{ row }">
            <el-tag
              :type="getUrgencyType(row.urgency)"
              @click="showTaskDetail(row)"
              style="cursor: pointer"
            >
              {{ getUrgencyText(row.urgency) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button @click="openCompleteDialog(row)">
              {{ row.completed ? '标记未完成' : '标记完成' }}
            </el-button>
            <el-button @click="openEditTaskDialog(row)">编辑</el-button>
            <!-- 移除删除按钮 -->
          </template>
        </el-table-column>
      </el-table>
      <!-- 添加分页组件 -->
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="tasks.total"
        @current-change="handlePageChange"
        layout="prev, pager, next"
      />
      <!-- 添加空状态提示 -->
      <el-empty v-if="tasks.list.length === 0 && !isLoading" description="暂无任务"></el-empty>
    </el-card>

    <!-- 添加任务对话框 -->
    <el-dialog v-model="showAddTaskDialog" title="添加任务">
      <el-form :model="newTaskForm" :rules="newTaskRules" ref="newTaskFormRef" label-width="80px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="newTaskForm.name" placeholder="请输入任务名称"></el-input>
        </el-form-item>
        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker
            v-model="newTaskForm.deadline"
            type="datetime"
            placeholder="选择截止时间"
          ></el-date-picker>
        </el-form-item>
        <!-- 添加任务进度输入框 -->
        <el-form-item label="任务进度" prop="progress">
          <el-slider
            v-model="newTaskForm.progress"
            :min="0"
            :max="100"
            show-input
            placeholder="请输入任务进度"
          />
        </el-form-item>
        <!-- 添加指派人选择 -->
        <el-form-item label="指派人" prop="assignee">
          <el-select v-model="newTaskForm.assignee" placeholder="请选择指派人">
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.username"
              :value="user.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="缓急程度" prop="urgency">
          <el-select v-model="newTaskForm.urgency" placeholder="请选择缓急程度">
            <el-option label="紧急" value="高" />
            <el-option label="重要" value="中" />
            <el-option label="一般" value="低" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddTaskDialog = false">取消</el-button>
          <el-button type="primary" @click="handleAddTask" :loading="isAddingTask">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑任务对话框 -->
    <el-dialog v-model="isEditTaskDialogVisible" title="编辑任务">
      <el-form :model="editTaskForm" :rules="newTaskRules" ref="editTaskFormRef" label-width="80px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="editTaskForm.name" placeholder="请输入任务名称"></el-input>
        </el-form-item>
        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker
            v-model="editTaskForm.deadline"
            type="datetime"
            placeholder="选择截止时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="任务进度" prop="progress">
          <el-slider
            v-model="editTaskForm.progress"
            :min="0"
            :max="100"
            show-input
            placeholder="请输入任务进度"
          />
        </el-form-item>
        <!-- 添加指派人选择 -->
        <el-form-item label="指派人" prop="assignee">
          <el-select v-model="editTaskForm.assignee" placeholder="请选择指派人">
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.username"
              :value="user.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="缓急程度" prop="urgency">
          <el-select v-model="editTaskForm.urgency" placeholder="请选择缓急程度">
            <el-option label="紧急" value="高" />
            <el-option label="重要" value="中" />
            <el-option label="一般" value="低" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="isEditTaskDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleEditTask" :loading="isEditingTask">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 任务详情对话框 -->
    <el-dialog v-model="isTaskDetailDialogVisible" title="任务详情">
      <el-form :model="taskDetail" label-width="100px">
        <el-form-item label="任务名称">
          <span>{{ taskDetail.name }}</span>
        </el-form-item>
        <el-form-item label="创建时间">
          <span>{{ formatDate(taskDetail.createTime) }}</span>
        </el-form-item>
        <el-form-item label="状态">
          <el-tag :type="taskDetail.completed ? 'success' : 'warning'">
            {{ taskDetail.completed ? '已完成' : '未完成' }}
          </el-tag>
        </el-form-item>
        <el-form-item label="任务进度">
          <el-progress :percentage="taskDetail.progress" :format="formatProgress" />
        </el-form-item>
        <el-form-item label="指派人">
          <span>{{ taskDetail.assigneeName || '未指派' }}</span>
        </el-form-item>
        <el-form-item label="缓急程度">
          <el-tag :type="getUrgencyType(taskDetail.urgency)">
            {{ getUrgencyText(taskDetail.urgency) }}
          </el-tag>
        </el-form-item>
        <el-form-item label="截止时间">
          <span>{{ formatDate(taskDetail.deadline) }}</span>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="isTaskDetailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 标记完成对话框 -->
    <el-dialog v-model="showCompleteDialog" title="标记完成">
      <el-form :model="completeForm" label-width="80px">
        <el-form-item label="上传文件">
          <el-upload
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            ref="uploadRef"
          >
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showCompleteDialog = false">取消</el-button>
          <el-button type="primary" @click="confirmCompleteTask" :loading="isCompletingTask">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '@/utils/request';
import { ElMessage, ElMessageBox, ElProgress, ElUpload } from 'element-plus';
import { formatDateTime } from '@/utils/date'; // 假设存在日期格式化工具函数

// 存储任务列表，包含分页信息
const tasks = ref({ list: [], total: 0 });
// 控制添加任务对话框显示隐藏
const showAddTaskDialog = ref(false);
// 新增任务表单数据
const newTaskForm = ref({
  name: '',
  deadline: null,
  progress: 0,
  assignee: '',
  urgency: '低'
});
// 新增任务表单验证规则
const newTaskRules = {
  name: [
    { required: true, message: '请输入任务名称', trigger: 'blur' }
  ],
  deadline: [
    { required: true, message: '请选择截止时间', trigger: 'change' }
  ],
  progress: [
    { required: true, message: '请输入任务进度', trigger: 'change' }
  ],
  assignee: [
    { required: true, message: '请选择指派人', trigger: 'change' }
  ],
  urgency: [
    { required: true, message: '请选择缓急程度', trigger: 'change' }
  ]
};
// 新增任务表单引用
const newTaskFormRef = ref(null);
// 搜索关键词
const searchQuery = ref('');
// 加载状态
const isLoading = ref(false);
// 添加任务加载状态
const isAddingTask = ref(false);

// 控制编辑任务对话框显示隐藏
const isEditTaskDialogVisible = ref(false);
// 编辑任务表单数据
const editTaskForm = ref({});
// 编辑任务表单引用
const editTaskFormRef = ref(null);
// 编辑任务加载状态
const isEditingTask = ref(false);

// 存储用户列表
const userList = ref([]);

// 当前页码
const currentPage = ref(1);
// 每页显示数量
const pageSize = ref(10);

// 进度格式化函数
const formatProgress = (percentage) => {
  return `${percentage}%`;
};

// 根据缓急程度返回对应的标签类型
const getUrgencyType = (urgency) => {
  switch (urgency) {
    case '高':
      return 'danger';
    case '中':
      return 'warning';
    case '低':
      return 'info';
    default:
      return 'info';
  }
};

// 根据缓急程度返回对应的文本
const getUrgencyText = (urgency) => {
  switch (urgency) {
    case '高':
      return '紧急';
    case '中':
      return '重要';
    case '低':
      return '一般';
    default:
      return '一般';
  }
};

// 定义缓急程度的优先级
const urgencyPriority = {
  '高': 1,
  '中': 2,
  '低': 3
};

// 控制标记完成对话框显示隐藏
const showCompleteDialog = ref(false);
// 标记完成表单数据
const completeForm = ref({
  fileUrl: ''
});
// 标记完成加载状态
const isCompletingTask = ref(false);
// 当前要标记完成的任务
const currentTask = ref(null);
// 上传组件引用
const uploadRef = ref(null);

// 获取任务列表
const fetchTasks = async () => {
  isLoading.value = true;
  try {
    const response = await request.get('/api/tasks/selectPage', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
        name: searchQuery.value
      }
    });
    tasks.value = response.data;
    // 对任务列表进行排序
    tasks.value.list.sort((a, b) => {
      // 先按创建时间降序排序
      const createTimeDiff = new Date(b.createTime) - new Date(a.createTime);
      if (createTimeDiff !== 0) {
        return createTimeDiff;
      }
      // 创建时间相同，按缓急程度排序
      return urgencyPriority[a.urgency] - urgencyPriority[b.urgency];
    });
  } catch (error) {
    console.error('获取任务列表失败:', error);
    ElMessage.error('获取任务列表失败，请稍后重试');
  } finally {
    isLoading.value = false;
  }
};

// 获取用户列表
const fetchUserList = async () => {
  try {
    const response = await request.get('/api/users');
    userList.value = response.data;
  } catch (error) {
    console.error('获取用户列表失败:', error);
    ElMessage.error('获取用户列表失败，请稍后重试');
  }
};

// 添加任务
const handleAddTask = async () => {
  if (!newTaskFormRef.value) return;
  isAddingTask.value = true;
  await newTaskFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await request.post('/api/tasks', newTaskForm.value);
        tasks.value.list.unshift(response.data);
        showAddTaskDialog.value = false;
        newTaskForm.value = {
          name: '',
          deadline: null,
          progress: 0,
          assignee: '',
          urgency: '低'
        };
        ElMessage.success('任务添加成功');
        fetchTasks(); // 重新获取任务列表
      } catch (error) {
        console.error('添加任务失败:', error);
        ElMessage.error('添加任务失败，请稍后重试');
      }
    } else {
      console.log('表单验证失败');
      return false;
    }
  });
  isAddingTask.value = false;
};

// 打开标记完成对话框
const openCompleteDialog = (task) => {
  if (task.completed) {
    toggleTask(task.id);
  } else {
    currentTask.value = task;
    showCompleteDialog.value = true;
    completeForm.value.fileUrl = '';
  }
};

// 处理文件上传成功
const handleUploadSuccess = (response) => {
  completeForm.value.fileUrl = response.url;
  ElMessage.success('文件上传成功');
};

// 处理文件上传失败
const handleUploadError = () => {
  ElMessage.error('文件上传失败，请重试');
};

// 确认标记完成任务
const confirmCompleteTask = async () => {
  if (!completeForm.value.fileUrl) {
    ElMessage.warning('请先上传完成文件');
    return;
  }
  isCompletingTask.value = true;
  try {
    const response = await request.put(`/api/tasks/${currentTask.value.id}`, {
      ...currentTask.value,
      completed: true,
      fileUrl: completeForm.value.fileUrl
    });
    const index = tasks.value.list.findIndex(t => t.id === currentTask.value.id);
    tasks.value.list[index] = response.data;
    showCompleteDialog.value = false;
    ElMessage.success('任务标记完成成功');
    fetchTasks(); // 重新获取任务列表
  } catch (error) {
    console.error('标记任务完成失败:', error);
    ElMessage.error('标记任务完成失败，请稍后重试');
  } finally {
    isCompletingTask.value = false;
    uploadRef.value.clearFiles();
  }
};

// 切换任务完成状态（标记未完成）
const toggleTask = async (id) => {
  const task = tasks.value.list.find(t => t.id === id);
  if (task) {
    try {
      const response = await request.put(`/api/tasks/${id}`, {
        ...task,
        completed: !task.completed,
        fileUrl: ''
      });
      const index = tasks.value.list.findIndex(t => t.id === id);
      tasks.value.list[index] = response.data;
      ElMessage.success('任务状态更新成功');
    } catch (error) {
      console.error('切换任务状态失败:', error);
      ElMessage.error('切换任务状态失败，请稍后重试');
    }
  }
};

// 显示编辑任务对话框
const openEditTaskDialog = (task) => {
  editTaskForm.value = { ...task };
  isEditTaskDialogVisible.value = true;
};

// 编辑任务
const handleEditTask = async () => {
  if (!editTaskFormRef.value) return;
  isEditingTask.value = true;
  await editTaskFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await request.put(`/api/tasks/${editTaskForm.value.id}`, editTaskForm.value);
        const index = tasks.value.list.findIndex(t => t.id === response.data.id);
        tasks.value.list[index] = response.data;
        isEditTaskDialogVisible.value = false;
        ElMessage.success('任务编辑成功');
        fetchTasks(); // 重新获取任务列表
      } catch (error) {
        console.error('编辑任务失败:', error);
        ElMessage.error('编辑任务失败，请稍后重试');
      }
    } else {
      console.log('表单验证失败');
      return false;
    }
  });
  isEditingTask.value = false;
};

// 搜索任务
const searchTasks = () => {
  currentPage.value = 1; // 搜索时重置到第一页
  fetchTasks();
};

// 处理分页页码变化
const handlePageChange = (page) => {
  currentPage.value = page;
  fetchTasks();
};

// 格式化日期
const formatDate = (date) => {
  return formatDateTime(date);
};

// 控制任务详情对话框显示隐藏
const isTaskDetailDialogVisible = ref(false);
// 任务详情数据
const taskDetail = ref({});

// 显示任务详情
const showTaskDetail = (task) => {
  taskDetail.value = { ...task };
  isTaskDetailDialogVisible.value = true;
};

onMounted(() => {
  fetchTasks();
  fetchUserList();
});
</script>

<style scoped>
.task-list-container {
  padding: 20px;
}

.task-list-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.task-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}

.el-table {
  margin-top: 20px;
}

.el-empty {
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
