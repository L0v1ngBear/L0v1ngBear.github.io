package org.example.springboot.service.impl;

import jakarta.annotation.Resource;
import org.example.springboot.entity.Task;
import org.example.springboot.mapper.TaskMapper;
import org.example.springboot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public Task createTask(Task task) {
        task.setCreateTime(LocalDateTime.now());
        taskMapper.save(task);
        return task;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskMapper.findById(id);
    }

    @Override
    public List<Task> getAllTasks(Task task) {
        return taskMapper.findAll(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        task.setId(id);
        taskMapper.update(task);
        return task;
    }
    

    @Override
    public List<Task> getTasksByAssignee(int assignee) {

        return taskMapper.findByAssignee(assignee);
    }

    public PageInfo<Task> selectPage(Task task, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Task> list= taskMapper.findAll(task);
        return PageInfo.of(list);
    }

    public PageInfo<Task> selectPageById(Task task, Integer pageNum,Integer pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Task> list= taskMapper.findAllById(task, id);
        return PageInfo.of(list);
    }
}
