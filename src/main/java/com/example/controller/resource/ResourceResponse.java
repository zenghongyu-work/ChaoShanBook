package com.example.controller.resource;

import com.example.domain.article.Article;
import com.example.domain.user.valueobject.Gender;
import com.example.domain.user.valueobject.Location;
import com.example.domain.video.Video;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class ResourceResponse {

    @Data
    @Builder
    @AllArgsConstructor
    public static class Resource {

        @ApiModelProperty("类型")
        private String type;

        @ApiModelProperty("短视频")
        private Video video;

        @ApiModelProperty("文章")
        private Article article;
    }
}
