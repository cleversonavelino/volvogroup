package com.volvogroup.test.address.service;


import com.volvogroup.test.address.dto.entity.Address;

public interface AddressService {

    Address create(Address address);

    Address get(Integer id);

    Iterable<Address> list();

    Address edit(Address address);

    void delete(Integer id);

}
