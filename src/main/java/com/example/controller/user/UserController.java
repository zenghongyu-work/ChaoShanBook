package com.example.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.app.UserApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.domain.user.User;
import com.example.infrastructure.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.controller.user.UserRequest.*;
import com.example.controller.user.UserResponse.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Api(tags = {"用户接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserApp userApp;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "用户注册")
    @PostMapping
    public Result register(@RequestBody Register request) {
        User user = User.builder().build();
        BeanUtils.copyProperties(request, user);
        userApp.register(user);
        return Result.builder()
                .msg("注册成功")
                .data(Token.builder().token(user.getToken()).build())
                .build();
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pd", value = "密码", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/login")
    public Result login(@RequestParam String name, @RequestParam String pd) {
        return Result.builder()
                .msg("登录成功")
                .data(Token.builder().token(userApp.login(name, pd).getToken()).build())
                .build();
    }

    @ApiOperation(value = "根据Token获取用户详情")
    @GetMapping
    public Result getByToken() {
        User user = userApp.getById(operator.getId());
        SimpleUser simpleUser = SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);
        return Result.builder()
                .data(simpleUser)
                .build();
    }

    @ApiOperation(value = "根据Id获取用户详情")
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        User user = userApp.getById(id);
        SimpleUser simpleUser = SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);
        return Result.builder()
                .data(simpleUser)
                .build();
    }

    @ApiOperation(value = "更新")
    @ApiImplicitParams({@ApiImplicitParam(name = "request", value = "更新表单", required = true, dataType = "com.example.controller.user.UserRequest.Update.class", paramType = "form")})
    @PutMapping
    public Result update(Update request) {
        User user = userApp.getById(operator.getId());

        BeanUtil.copyProperties(request, user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));

        if (request.getIcon() != null) {
            user.setIcon(UploadUtils.uploadPicture(new MultipartFile[]{request.getIcon()}).get(0).getPath());
        }

        userApp.update(user);

        SimpleUser simpleUser = SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);

        return Result.builder()
                .msg("更新成功")
                .data(simpleUser)
                .build();
    }

    @ApiOperation(value = "更新昵称")
    @PutMapping("/nickname")
    public Result updateNickname(@RequestBody UpdateNickname request) {
        User user = userApp.getById(operator.getId());
        BeanUtils.copyProperties(request, user);
        userApp.updateNickname(user);
        SimpleUser simpleUser = SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);
        return Result.builder()
                .msg("更新成功")
                .data(simpleUser)
                .build();
    }
}
