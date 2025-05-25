package org.example.springboot.service.impl;

import org.example.springboot.common.Result;
import org.example.springboot.dto.ChangePasswordDTO;
import org.example.springboot.dto.LoginDTO;
import org.example.springboot.entity.User;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public Result changePassword(ChangePasswordDTO changePasswordDTO) {
        // 这里需要根据实际业务获取当前用户，假设通过某种方式获取到用户 ID
        Long userId = 1L; // 示例用户 ID，实际需要替换为正确的获取方式
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 验证旧密码是否正确，假设密码未加密存储，实际应使用加密比较
        if (!user.getPassword().equals(changePasswordDTO.getOldPassword())) {
            return Result.error("旧密码错误");
        }

        // 验证新密码和确认密码是否一致
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            return Result.error("两次输入的新密码不一致");
        }

        // 更新密码
        user.setPassword(changePasswordDTO.getNewPassword());
        int rows = userMapper.updateById(user);
        if (rows > 0) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("密码修改失败");
        }
    }

    @Override
    public User validateUser(LoginDTO loginDTO) {
        return userMapper.selectUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    }
}
