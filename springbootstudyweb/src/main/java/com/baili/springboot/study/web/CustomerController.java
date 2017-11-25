package com.baili.springboot.study.web;

import com.baili.springboot.study.es.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @author jiangjingming
 */
@RestController
@RequestMapping(value = "/es")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object test() throws IOException {
        customerService.saveCustomers();
        customerService.fetchAllCustomers();
        customerService.fetchIndividualCustomers();
        return true;
    }
}
