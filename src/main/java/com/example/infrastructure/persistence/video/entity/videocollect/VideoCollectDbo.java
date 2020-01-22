package com.example.infrastructure.persistence.video.entity.videocollect;

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
@Table(name = "video_collect")
public class VideoCollectDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer videoId;
    private Integer userId;
    private String collectTime;
}
