package org.example.springboot.controller;

import com.github.pagehelper.PageInfo;
import org.example.springboot.common.Result;
import org.example.springboot.entity.Task;
import org.example.springboot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping
    public Result createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return Result.success(createdTask);
    }

    @GetMapping("/{id}")
    public Result getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return Result.success(task);
    }

    @GetMapping("/selectAll")
    public Result getAllTasks(Task task) {
        List<Task> tasks = taskService.getAllTasks(task);
        return Result.success(tasks);
    }

    @GetMapping("/selectPage")
    public Result getTaskPage(Task task, @RequestParam int page, @RequestParam int size) {
        PageInfo<Task> taskPage = taskService.selectPage(task, page, size);
        return Result.success(taskPage);
    }

    @GetMapping("/assignee/{assignee}")
    public Result getTasksByAssignee(@PathVariable int assignee) {
        List<Task> tasks = taskService.getTasksByAssignee(assignee);
        return Result.success(tasks);
    }

    @PutMapping("/{id}")
    public Result updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return Result.success(updatedTask);
    }

    @GetMapping("/selectPageById/{id}")
    public Result getTaskPageById(Task task, @RequestParam int page, @RequestParam int size, @PathVariable Long id) {
        PageInfo<Task> taskPage = taskService.selectPageById(task, page, size, id);
        return Result.success(taskPage);
    }
}
