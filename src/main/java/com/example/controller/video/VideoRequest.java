package com.example.controller.video;

import com.example.domain.user.valueobject.Gender;
import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class VideoRequest {

    @Data
    public static class CreateVideo {

        @ApiModelProperty("标题")
        private String title;

        @ApiModelProperty("内容")
        private String content;

        @ApiModelProperty("短视频")
        private MultipartFile video;
    }
}
