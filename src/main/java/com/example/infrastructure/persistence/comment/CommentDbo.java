package com.example.infrastructure.persistence.comment;

import com.example.infrastructure.persistence.BaseDbo;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "comment")
public class CommentDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String content;
    private Integer userId;
    private String userNickname;
    private String userIcon;
    private Integer relationId;
    private String type;
    private Integer praiseCount;
    private Integer commentId;
    private Integer replyId;
    private Integer toUserId;
    private String toUserNickname;
    private String toUserIcon;
    private String createAt;
    private String status;

}
