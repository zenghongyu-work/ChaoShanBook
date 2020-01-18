package com.example.domain.article;

import com.example.domain.article.valueobject.Picture;
import com.example.domain.media.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Media {
    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String content = "";

    @Builder.Default
    private List<Picture> pictures = Collections.emptyList();

    @Builder.Default
    private Integer praiseCount = 0;

    @Builder.Default
    private Integer createBy = 0;

    @Builder.Default
    private String createAt = "";
}
