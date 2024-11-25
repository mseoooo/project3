package com.example.jwtproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.jwtproject.model.User;

@Mapper
public interface UserMapper {
    User findByUsername(String id);
    void save(User id);
}
