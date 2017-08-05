package com.baili.springboot.study.web;

import com.baili.entity.Student;
import com.baili.entity.StudentCriteria;
import com.baili.springboot.core.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Spring Boot HelloWorld 案例
 *
 * Created by bysocket on 16/4/26.
 */
@Controller
public class HelloWorldController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/all")
    @ResponseBody
    public String sayHello() {
        return "Hello,World!";
    }

    @ResponseBody
    @RequestMapping("/studentList")
    public List<Student> studentList() {
        StudentCriteria criteria = new StudentCriteria();
        //criteria.createCriteria().
        List<Student> studentList = studentService.selectStudents(criteria);
        return studentList;
    }
}
