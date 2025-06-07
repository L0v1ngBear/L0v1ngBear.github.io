package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAll();

    User selectById(Long id);

    int updateById(User user);

    User selectUserByUsername(String username);
}
