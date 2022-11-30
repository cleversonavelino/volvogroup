package com.volvogroup.test.customer.repository;

import com.volvogroup.test.customer.dto.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query(value="select c from Customer c join c.addresses a where a.zipCode = :zipCode", nativeQuery = false)
    List<Customer> findByZipCode(@Param("zipCode") String zipCode);

}
