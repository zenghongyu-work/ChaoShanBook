package com.example.controller.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class FollowerRequest {

    @Data
    public static class Follow {

        @ApiModelProperty("被关注用户")
        private Integer userId;
    }
}
