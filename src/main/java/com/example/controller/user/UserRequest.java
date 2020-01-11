package com.example.controller.user;

import com.example.domain.user.valueobject.Gender;
import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class UserRequest {

    @Data
    public static class Register {

        @ApiModelProperty("用户名")
        private String name;

        @ApiModelProperty("密码")
        private String pd;

        @ApiModelProperty("电话")
        private String phone;

        @ApiModelProperty("性别")
        private Gender gender;

        @ApiModelProperty("头像")
        private String icon;

        @ApiModelProperty("地址")
        private Location location;
    }

    @Data
    public static class UpdateNickname {

        @ApiModelProperty("昵称")
        private String nickname;
    }
}
