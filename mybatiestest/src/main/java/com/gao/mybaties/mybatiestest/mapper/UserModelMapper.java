package com.gao.mybaties.mybatiestest.mapper;

import com.gao.mybaties.mybatiestest.model.UserModel;

public interface UserModelMapper {
    int deleteByPrimaryKey(Integer useId);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Integer useId);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);
}