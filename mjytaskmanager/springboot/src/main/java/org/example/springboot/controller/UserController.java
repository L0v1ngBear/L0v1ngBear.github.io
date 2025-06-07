package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.dto.ChangePasswordDTO;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result selectAll() {
        List<User> users = userService.selectAll();
        return Result.success(users);
    }

    @PutMapping("/password")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(changePasswordDTO);
    }
}
