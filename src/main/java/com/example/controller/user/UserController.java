package com.example.controller.user;

import com.example.app.UserApp;
import com.example.controller.common.Operator;
import com.example.domain.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.controller.user.UserRequest.*;

@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserApp userApp;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public User register(@RequestBody Register request) {
        User user = User.builder().build();
        BeanUtils.copyProperties(request, user);
        userApp.register(user);
        return user;
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pd", value = "密码", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/login")
    public User login(@RequestParam String name, @RequestParam String pd) {
        return userApp.login(name, pd);
    }

    @ApiOperation(value = "更新昵称")
    @PutMapping("/nickname")
    public User updateNickname(@RequestBody UpdateNickname request) {
        User user = userApp.getById(operator.getId());
        BeanUtils.copyProperties(request, user);
        userApp.updateNickname(user);
        return user;
    }
}
