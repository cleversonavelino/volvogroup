package com.volvogroup.test.address.resource;

import com.volvogroup.test.address.dto.AddressDto;
import com.volvogroup.test.address.dto.entity.Address;
import com.volvogroup.test.address.service.AddressService;
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
@RequestMapping("/api/address")
public class AddressResource {

    private AddressService addressService;

    private ModelMapper modelMapper;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
        this.modelMapper = new ModelMapper();
    }

    @Operation(description = "Retorna o endereço com ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao endereço"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> get(@PathVariable Integer id) {
        try {
            Address address = addressService.get(id);
            AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
            return ResponseEntity.ok(addressDto);
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Lista de endereços do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços"),
    })
    @GetMapping("/list")
    public List<AddressDto> list() {
        Iterable<Address> addressList = addressService.list();
        return StreamSupport.stream(addressList.spliterator(),false)
                .map(source -> modelMapper.map(source, AddressDto.class))
                .collect(Collectors.toList());
    }

    @Operation(description = "Cria novo consumidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao novo endereço"),
    })
    @PostMapping("/save")
    public ResponseEntity<AddressDto> create(@Valid @RequestBody AddressDto addressDto) {
        Address address = this.modelMapper.map(addressDto, Address.class);
        address = addressService.create(address);
        addressDto.setId(address.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto);
    }

    @Operation(description = "Modifica o endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto correspondente ao endereço modificado"),
    })
    @PutMapping("/edit")
    public ResponseEntity<AddressDto> edit(@Valid @RequestBody AddressDto addressDto) {
        Address address = this.modelMapper.map(addressDto, Address.class);
        this.addressService.edit(address);
        return ResponseEntity.ok(addressDto);
    }

    @Operation(description = "Exclui o endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto vazio"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        addressService.delete(id);
    }
}
