package com.baili.springboot.core.service;

import com.baili.entity.Student;
import com.baili.entity.StudentCriteria;

import java.util.List;

/**
 * Created by jiangjingming on 2017/6/13.
 */
public interface IStudentService {

    List<Student> selectStudents(StudentCriteria criteria);
}
