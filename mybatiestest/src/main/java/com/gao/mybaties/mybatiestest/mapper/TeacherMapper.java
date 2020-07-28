package com.gao.mybaties.mybatiestest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gao.mybaties.mybatiestest.model.Teacher;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
    
    Teacher select(@Param("teacherId")Integer id,@Param("teacherName") String name);
    
    
    int batchDelete(List<Teacher> teachers);
    
    
    
}