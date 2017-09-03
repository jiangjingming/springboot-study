package com.baili.springboot.study.es.service;

import com.baili.springboot.study.es.dao.Customer;
import com.baili.springboot.study.es.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @version v0.0.1
 * @Description 业务层
 * @Author wanwt@senthink.com
 * @Creation Date 2017年03月30日 下午8:19
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public void saveCustomers() throws IOException {
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));
    }

    public void fetchAllCustomers() throws IOException {
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
    }

    public void fetchIndividualCustomers() {
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }
}