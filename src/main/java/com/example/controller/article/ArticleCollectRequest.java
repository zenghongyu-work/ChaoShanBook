package com.example.controller.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class ArticleCollectRequest {

    @Data
    public static class Collect {

        @ApiModelProperty("操作")
        private String type;

        @ApiModelProperty("被收藏文章")
        private Integer articleId;
    }
}
