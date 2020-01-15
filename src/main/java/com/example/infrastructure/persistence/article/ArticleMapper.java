package com.example.infrastructure.persistence.article;

import com.example.infrastructure.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleDbo> {

    @Select("select id,title,content,praise_count as praiseCount,create_by as createBy,create_at as createAt from article order by rand() limit #{size}")
    List<ArticleDbo> listRandom(Integer size);
}
