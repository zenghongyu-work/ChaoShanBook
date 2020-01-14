package com.example.infrastructure.persistence.video;

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
@Table(name = "video")
public class VideoDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    private String content;
    private String video;
    private Integer praiseCount;
    private Integer createBy;
    private String createAt;
}
