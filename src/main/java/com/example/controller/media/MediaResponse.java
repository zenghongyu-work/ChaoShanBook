package com.example.controller.media;

import com.example.domain.media.Media;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class MediaResponse {

    @Data
    @Builder
    @AllArgsConstructor
    public static class Media {

        @ApiModelProperty("类型")
        private String type;

        @ApiModelProperty("媒体")
        private com.example.domain.media.Media media;
    }
}
