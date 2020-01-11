package com.example.domain.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String video = "";

    @Builder.Default
    private Integer praiseCount = 0;

    @Builder.Default
    private Integer createBy = 0;

    @Builder.Default
    private String createAt = "";
}
