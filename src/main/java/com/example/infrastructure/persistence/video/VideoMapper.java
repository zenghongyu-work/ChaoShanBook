package com.example.infrastructure.persistence.video;

import com.example.infrastructure.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper extends BaseMapper<VideoDbo> {
}
