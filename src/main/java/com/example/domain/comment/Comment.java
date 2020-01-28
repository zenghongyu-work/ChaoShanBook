package com.example.domain.comment;

import com.example.domain.common.EnumType;
import com.example.domain.user.valueobject.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.example.domain.common.EnumType.CommentStatus.NORMAL;
import static com.example.domain.common.EnumType.MediaType.VIDEO;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private String content = "";

    @Builder.Default
    private Integer userId = 0;

    @Builder.Default
    private String userNickname = "";

    @Builder.Default
    private String userIcon = "default.jpg";

    @Builder.Default
    private Integer relationId = 0;

    @Builder.Default
    private String type = "";

    @Builder.Default
    private Integer praiseCount = 0;

    @Builder.Default
    private Integer commentId = 0; // 父评论ID

    @Builder.Default
    private Integer replyId = 0; // 回复的评论ID

    @Builder.Default
    private Integer toUserId = 0;

    @Builder.Default
    private String toUserNickname = "";

    @Builder.Default
    private String toUserIcon = "default.jpg";

    @Builder.Default
    private String createAt = "";

    @Builder.Default
    private String status = NORMAL;

    @Builder.Default
    private List<Comment> subComments = Collections.emptyList();
}
