package com.example.domain.video.entity.videocollect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCollect {
    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private Integer videoId = 0;

    @Builder.Default
    private Integer userId = 0;

    @Builder.Default
    private String collectTime = "1900-01-01 00:00:00";
}
