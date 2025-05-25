package org.example.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean completed;
    private LocalDateTime createTime;
    private LocalDateTime deadline;
    private Integer progress;
    private String urgency;

    // 存储用户ID的字段（对应数据库assignee列）
    private Long assignee;

    // 存储关联查询得到的用户名（非数据库字段）
    @Transient
    private String assigneeName;
}