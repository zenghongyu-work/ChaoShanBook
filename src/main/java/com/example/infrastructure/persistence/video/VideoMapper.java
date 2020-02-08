package com.example.infrastructure.persistence.video;

import com.example.infrastructure.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<VideoDbo> {

    @Select("select id, title, content, video, snapshot, praise_count as praiseCount,create_by as createBy,create_at as createAt from video order by rand() * (praise_count+1) desc limit #{size}")
    List<VideoDbo> listRandom(Integer size);
}
