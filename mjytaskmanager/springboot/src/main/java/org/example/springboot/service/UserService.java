package org.example.springboot.service;

import org.example.springboot.common.Result;
import org.example.springboot.dto.ChangePasswordDTO;
import org.example.springboot.entity.User;

import java.util.List;

public interface UserService {
    List<User> selectAll();

    Result changePassword(ChangePasswordDTO changePasswordDTO);

    User validateUser(String username);
}
