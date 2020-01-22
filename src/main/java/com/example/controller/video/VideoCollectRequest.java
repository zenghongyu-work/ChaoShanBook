package com.example.controller.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class VideoCollectRequest {

    @Data
    public static class Collect {

        @ApiModelProperty("操作")
        private String type;

        @ApiModelProperty("被收藏视频")
        private Integer videoId;
    }
}
