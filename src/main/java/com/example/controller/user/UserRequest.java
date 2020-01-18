package com.example.controller.user;

import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    @Data
    public static class Register {

        @ApiModelProperty("用户名")
        private String name;

        @ApiModelProperty("密码")
        private String pd;
    }

    @Data
    public static class Update {

        @ApiModelProperty("昵称")
        private String nickname;

        @ApiModelProperty("性别")
        private String gender;

        @ApiModelProperty("地址")
        private Location location;

        @ApiModelProperty("生日")
        private String birthday;

        @ApiModelProperty("签名")
        private String signature;
    }

    @Data
    public static class UpdateIcon {

        @ApiModelProperty("头像")
        private MultipartFile icon;
    }

}
