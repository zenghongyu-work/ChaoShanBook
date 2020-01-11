package com.example.infrastructure.persistence.user;

import com.example.infrastructure.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDbo> {
}
