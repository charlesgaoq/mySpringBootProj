package com.gao.boot.bootest;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer useId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer useId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}