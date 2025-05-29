import { defineStore } from 'pinia';

export const useTodoStore = defineStore('todo', {
  state: () => ({
    todoCount: 0
  }),
  actions: {
    setTodoCount(count) {
      this.todoCount = count;
    }
  }
});
