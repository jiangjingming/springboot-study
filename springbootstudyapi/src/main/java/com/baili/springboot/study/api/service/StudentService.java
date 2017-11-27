package com.baili.springboot.study.api.service;

import com.baili.springboot.study.api.model.StudentDTO;

import java.util.List;

/**
 *
 * @author jiangjingming
 * @date 2017/8/6
 */
public interface StudentService {

    /**
     * 查询Id
     * @param id
     * @return
     */
    List<StudentDTO> selectStudents(Long id);
}
