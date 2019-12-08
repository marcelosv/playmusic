package com.marcelsouzav.udemy.customer.save.service.service;

import com.marcelsouzav.udemy.customer.save.service.domain.Customer;
import com.marcelsouzav.udemy.customer.save.service.gateway.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public UUID execute(Customer customer) {
        customer.setId(UUID.randomUUID());
        customerRepository.save(customer);
        return customer.getId();
    }

}