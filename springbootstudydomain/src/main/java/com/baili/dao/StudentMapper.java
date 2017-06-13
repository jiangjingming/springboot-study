package com.baili.dao;

import com.baili.entity.Student;
import com.baili.entity.StudentCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentMapper {
    long countByExample(StudentCriteria example);

    int deleteByExample(StudentCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExampleWithRowbounds(StudentCriteria example, RowBounds rowBounds);

    List<Student> selectByExample(StudentCriteria example);

    Student selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentCriteria example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentCriteria example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}