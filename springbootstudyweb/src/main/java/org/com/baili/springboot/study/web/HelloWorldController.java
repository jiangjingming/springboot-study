package org.com.baili.springboot.study.web;

import com.baili.dao.StudentMapper;
import com.baili.entity.Student;
import com.baili.entity.StudentCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Spring Boot HelloWorld 案例
 *
 * Created by bysocket on 16/4/26.
 */
@RestController
public class HelloWorldController {

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @RequestMapping("/studentList")
    public List<Student> studentList() {
        StudentCriteria criteria = new StudentCriteria();
        //criteria.createCriteria().
        List<Student> studentList = studentMapper.selectByExample(criteria);
        return studentList;
    }
}
