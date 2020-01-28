package com.example.controller.comment;

import com.example.domain.user.valueobject.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class CommentRequest {

    @Data
    public static class Create {

        @ApiModelProperty("评论内容")
        private String content;

        @ApiModelProperty("评论的资源ID")
        private Integer relationId;

        @ApiModelProperty("评论的资源类型")
        private String type;

        @ApiModelProperty("父评论ID")
        private Integer commentId; // 第一级评论没父评论ID

        @ApiModelProperty("回复评论ID")
        private Integer replyId; // 第一级评论没回复评论ID

        @ApiModelProperty("回复评论的创建用户ID")
        private Integer toUserId;
    }

    @Data
    public static class Praise {

        @ApiModelProperty("操作")
        private String type;

        @ApiModelProperty("被点赞评论")
        private Integer id;
    }
}
