package com.example.infrastructure.persistence.comment;

import com.example.infrastructure.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<CommentDbo> {
}
