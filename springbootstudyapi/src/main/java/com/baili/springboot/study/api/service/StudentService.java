package com.baili.springboot.study.api.service;

import com.baili.springboot.study.api.model.StudentDTO;

import java.util.List;

/**
 * Created by jiangjingming on 2017/8/6.
 */
public interface StudentService {

    List<StudentDTO> selectStudents(Long id);
}
