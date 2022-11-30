package com.volvogroup.test.address.service;

import com.volvogroup.test.address.dto.entity.Address;
import com.volvogroup.test.address.repository.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address get(Integer id) {
        return addressRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public Iterable<Address> list() {
        return addressRepository.findAll();
    }

    @Override
    public Address edit(Address customer) {
        Address savedCustomer = this.get(customer.getId());
        BeanUtils.copyProperties(customer, savedCustomer, "id");
        return addressRepository.save(savedCustomer);
    }

    @Override
    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }

}
