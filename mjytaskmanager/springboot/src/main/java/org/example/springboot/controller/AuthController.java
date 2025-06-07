package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.dto.LoginDTO;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.example.springboot.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        User user = userService.validateUser(loginDTO.getUsername());
        if (user != null && BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername(), user.getId());
            user.setToken(token);
            return Result.success(user.getToken());
        } else {
            return Result.error("用户名、密码或角色错误");
        }
    }
}
