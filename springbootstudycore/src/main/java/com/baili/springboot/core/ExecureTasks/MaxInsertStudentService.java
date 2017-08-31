package com.baili.springboot.core.ExecureTasks;

import com.baili.entity.Student;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by jiangjingming on 2017/8/16.
 */
@Service
public class MaxInsertStudentService {

    @Async
    public Future<String> task1() {
        Student student = new Student();
        return new AsyncResult<>("任务一完成");
    }
    @Async
    public Future task2() {
        Student student = new Student();
        return new AsyncResult<>("任务一完成");
    }
    @Async
    public Future task3() {
        Student student = new Student();
        return new AsyncResult<>("任务一完成");
    }
    @Async
    public Future task4() {
        Student student = new Student();
        return new AsyncResult<>("任务一完成");
    }
}
