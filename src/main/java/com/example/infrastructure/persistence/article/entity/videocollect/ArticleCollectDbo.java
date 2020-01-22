package com.example.infrastructure.persistence.article.entity.videocollect;

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
@Table(name = "article_collect")
public class ArticleCollectDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String collectTime;
}
