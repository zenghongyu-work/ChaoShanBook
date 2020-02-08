package com.example.domain.video;

import com.example.domain.media.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Media {
    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String content = "";

    @Builder.Default
    private String video = "";

    @Builder.Default
    private String snapshot = "";

    @Builder.Default
    private Integer praiseCount = 0;

    @Builder.Default
    private Integer createBy = 0;

    @Builder.Default
    private String createAt = "";
}
