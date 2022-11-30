package com.volvogroup.test.address.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddressDto {

    private Integer id;

    @NotNull
    @Pattern(regexp = "(^\\d{5}[-]\\d{3}$)")
    private String zipCode;

    private String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
