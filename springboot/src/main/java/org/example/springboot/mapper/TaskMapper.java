package org.example.springboot.mapper;

import org.example.springboot.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {


    /**
     * 保存任务
     * @param task 任务实体
     */
    void save(Task task);

    /**
     * 根据任务 ID 查询任务
     * @param id 任务 ID
     * @return 任务实体 Optional 对象
     */
    Task findById(Long id);

    /**
     * 查询所有任务
     * @return 任务列表
     */
    List<Task> findAll(Task task);

    /**
     * 根据任务 ID 更新任务
     * @param task 任务实体
     */
    void update(Task task);

    List<Task> findByAssignee(int assignee);

    List<Task> findAllById(Task task, Long id);
}

