package com.example.controller.article;

import com.example.domain.article.valueobject.Picture;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public class ArticleRequest {

    @Data
    public static class CreateArticle {

        @ApiModelProperty("标题")
        private String title;

        @ApiModelProperty("内容")
        private String content;

        @ApiModelProperty("图片")
        MultipartFile[] pictures;
    }

}
