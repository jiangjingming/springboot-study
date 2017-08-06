package com.baili.springboot.core.dubbo.service;

import com.baili.dao.StudentMapper;
import com.baili.entity.Student;
import com.baili.springboot.study.api.model.StudentDTO;
import com.baili.springboot.study.api.service.StudentService;
import com.baili.springboot.study.common.util.BeanCopierUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangjingming on 2017/8/6.
 */
@Service("dubboStudentService")
public class DubboStudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<StudentDTO> selectStudents(Long id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        StudentDTO studentDTO = new StudentDTO();
        BeanCopierUtil.copy(student,studentDTO);
        return Lists.newArrayList(studentDTO);
    }
}
