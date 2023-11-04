package com.tzz.controller;

import com.tzz.entity.UserEntity;
import com.tzz.server.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户")
    public UserEntity addUser(@RequestBody UserEntity u){

        return userService.addUser(u);
    }

    @GetMapping("/login/{name}/{passWord}")
    @ApiOperation(value = "用户登录")
    public UserEntity login(@PathVariable String name, @PathVariable String passWord) {
        return userService.login(name,passWord);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新用户信息")
    public UserEntity updateUser(@PathVariable int id, @RequestBody UserEntity newUserEntity){
        UserEntity userEntity= userService.updateUser(id, newUserEntity);

        if (userEntity!=null)
            return userService.updateUser(id, newUserEntity);
        else
            return null;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户id查询用户信息")
    public UserEntity getUserInfoById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

}
