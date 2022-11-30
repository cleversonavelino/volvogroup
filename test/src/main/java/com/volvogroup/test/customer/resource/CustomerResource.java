package com.volvogroup.test.customer.resource;

import com.volvogroup.test.customer.dto.CustomerDto;
import com.volvogroup.test.customer.dto.entity.Customer;
import com.volvogroup.test.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    private CustomerService customerService;

    private ModelMapper modelMapper;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
        this.modelMapper = new ModelMapper();
    }

    @Operation(description = "Retorna o consumidor com ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao consumidor"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable Integer id) {
        try {
            Customer customer = customerService.get(id);
            CustomerDto customerDto = this.modelMapper.map(customer, CustomerDto.class);
            return ResponseEntity.ok(customerDto);
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Lista de consumidores do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consumidores"),
    })
    @GetMapping("/list")
    public List<CustomerDto> list() {
        Iterable<Customer> customerList = customerService.list();
        return StreamSupport.stream(customerList.spliterator(),false)
                .map(source -> modelMapper.map(source, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Operation(description = "Cria novo consumidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao novo consumidor"),
    })
    @PostMapping("/save")
    public ResponseEntity<CustomerDto> create(@Valid  @RequestBody CustomerDto customerDto) {
        Customer customer = this.modelMapper.map(customerDto, Customer.class);
        customer = customerService.create(customer);
        customerDto.setId(customer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

    @Operation(description = "Modifica o consumidor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao consumidor modificado"),
    })
    @PutMapping("/edit")
    public ResponseEntity<CustomerDto> edit(@Valid  @RequestBody CustomerDto customerDto) {
        Customer customer = this.modelMapper.map(customerDto, Customer.class);
        this.customerService.edit(customer);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(description = "Exclui o consumidor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto vazio"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        customerService.delete(id);
    }

    @Operation(description = "Retorna o consumidor com o cep fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao consumidor"),
    })
    @GetMapping("/address/{ZipCode}")
    public List<CustomerDto> getByZipCode(@PathVariable String ZipCode) {
        Iterable<Customer> customerList = customerService.getByZipCode(ZipCode);
        return StreamSupport.stream(customerList.spliterator(),false)
                .map(source -> modelMapper.map(source, CustomerDto.class))
                .collect(Collectors.toList());
    }
}
