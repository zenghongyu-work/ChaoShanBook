package com.example.controller.video;

import com.example.domain.user.valueobject.Gender;
import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class VideoRequest {

    @Data
    public static class CreateVideo {

        @ApiModelProperty("标题")
        private String title;

        @ApiModelProperty("短视频路径")
        private String video;
    }

    @Data
    public static class UpdateNickname {

        @ApiModelProperty("昵称")
        private String nickname;
    }
}
