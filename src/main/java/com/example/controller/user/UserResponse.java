package com.example.controller.user;

import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class UserResponse {

    @Data
    @Builder
    @AllArgsConstructor
    public static class Token {

        @ApiModelProperty("token")
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class SimpleUser {

        @ApiModelProperty("用户Id")
        private Integer id;

        @ApiModelProperty("用户名")
        private String name;

        @ApiModelProperty("昵称")
        private String nickname;

        @ApiModelProperty("电话")
        private String phone;

        @ApiModelProperty("性别")
        private String gender;

        @ApiModelProperty("生日")
        private String birthday;

        @ApiModelProperty("签名")
        private String signature;

        @ApiModelProperty("头像")
        private String icon;

        @ApiModelProperty("地址")
        private Location location;
    }
}
