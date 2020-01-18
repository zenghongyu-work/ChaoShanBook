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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.example.controller.user.UserRequest.*;
import com.example.controller.user.UserResponse.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = {"用户接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${picture-base-url}")
    private String pictureBaseUrl;

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

        return Result.builder()
                .data(toSimpleUser(user))
                .build();
    }

    @ApiOperation(value = "根据Id获取用户详情")
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        User user = userApp.getById(id);

        return Result.builder()
                .data(toSimpleUser(user))
                .build();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    public Result update(@RequestBody Update request) {
        User user = userApp.getById(operator.getId());

        BeanUtil.copyProperties(request, user,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));

        userApp.update(user);
        
        return Result.builder()
                .msg("更新成功")
                .data(toSimpleUser(user))
                .build();
    }

    private SimpleUser toSimpleUser(User user) {
        SimpleUser simpleUser = SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);

        if (StringUtils.isNotBlank(simpleUser.getIcon())) {
            simpleUser.setIcon(pictureBaseUrl + simpleUser.getIcon());
        }

        return simpleUser;
    }

    @ApiOperation(value = "更新头像")
    @ApiImplicitParams({@ApiImplicitParam(name = "icon", value = "头像", required = true, dataType = "MultipartFile", paramType = "form")})
    @PostMapping("/update/icon")
    public Result updateIcon(@RequestParam MultipartFile icon) {
        User user = userApp.getById(operator.getId());

        if (icon != null) {
            user.setIcon(UploadUtils.uploadPicture(new MultipartFile[]{icon}).get(0).getPath());
        }

        userApp.update(user);

        return Result.builder()
                .msg("更新成功")
                .data(toSimpleUser(user))
                .build();
    }

}
