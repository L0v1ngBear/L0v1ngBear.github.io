package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks(Task task);
    Task updateTask(Long id, Task task);
    List<Task> getTasksByAssignee(int assignee);
    PageInfo<Task> selectPage(Task Task, Integer pageNum,Integer pageSize);
    PageInfo<Task> selectPageById(Task Task, Integer pageNum,Integer pageSize, Long id);
}
