package com.volvogroup.test.customer.service;

import com.volvogroup.test.customer.dto.entity.Customer;
import com.volvogroup.test.customer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        Assert.notNull(customer, "consultant.service.consultant.null");
        return customerRepository.save(customer);
    }

    @Override
    public Customer get(Integer id) {
        Assert.notNull(id, "consultant.service.id.null");
        return customerRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public Iterable<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Customer edit(Customer customer) {
        Customer savedCustomer = this.get(customer.getId());
        BeanUtils.copyProperties(customer, savedCustomer, "id");
        return customerRepository.save(savedCustomer);
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getByZipCode(String ZipCode) {
        return  customerRepository.findByZipCode(ZipCode);
    }

}
