package com.cad.controller;

import com.cad.entity.Result;
import com.cad.pojo.AppUsers;
import com.cad.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * “我的”模块controller
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/my")
public class MyController {
    @Autowired
    private MyService myService;

    // 注册功能
    @PostMapping("/register")
    public Result register(@RequestBody AppUsers appUsers) {
        return myService.register(appUsers);
    }

    // 登录功能
    @PostMapping("/login")
    public Result login(@RequestBody AppUsers appUsers) {
        String nickname = appUsers.getNickname();
        String password = appUsers.getPassword();
        return myService.login(nickname, password);
    }

    @PostMapping("info")
    public AppUsers info(@RequestBody AppUsers appUsers) {
        String nickname = appUsers.getNickname();
        return myService.info(nickname);
    }
}
