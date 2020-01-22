package com.example.domain.article.entity.articlecollect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCollect {
    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private Integer articleId = 0;

    @Builder.Default
    private Integer userId = 0;

    @Builder.Default
    private String collectTime = "1900-01-01 00:00:00";
}
