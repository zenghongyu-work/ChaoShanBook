package com.example.infrastructure.persistence.article;

import com.example.infrastructure.persistence.BaseDbo;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "article")
public class ArticleDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    private String content;
    private Integer praiseCount;
    private Integer createBy;
    private String createAt;
}
