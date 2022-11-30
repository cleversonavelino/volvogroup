package com.volvogroup.test.customer.service;


import com.volvogroup.test.customer.dto.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer create(Customer customer);

    Customer get(Integer id);

    Iterable<Customer> list();

    Customer edit(Customer customer);

    void delete(Integer id);

    List<Customer> getByZipCode(String ZipCode);
}
