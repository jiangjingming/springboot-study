package com.baili.springboot.core.service.impl;

import com.baili.dao.StudentMapper;
import com.baili.entity.Student;
import com.baili.entity.StudentCriteria;
import com.baili.springboot.core.service.IStudentService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangjingming on 2017/6/13.
 */
@Service
public class StudnentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Student> selectStudents(StudentCriteria criteria) {
        criteria.createCriteria().andNameLike("%bai%");
        return studentMapper.selectByExampleWithRowbounds(criteria, new RowBounds(1,2));
    }
}
