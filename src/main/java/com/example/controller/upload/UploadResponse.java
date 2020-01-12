package com.example.controller.upload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UploadResponse {

    @Data
    @Builder
    @AllArgsConstructor
    public static class Picture {

        @ApiModelProperty("图片路径列表")
        private List<String> paths;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Video {

        @ApiModelProperty("视频路径")
        private String path;
    }
}
