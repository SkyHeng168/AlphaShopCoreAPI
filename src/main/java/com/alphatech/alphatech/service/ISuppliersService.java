package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRequest;
import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRespond;

import java.io.IOException;
import java.util.List;

public interface ISuppliersService {
    List<SuppliersRespond> findAllSuppliers();
    SuppliersRespond createSuppliers(SuppliersRequest suppliersRequest) throws Exception;
    SuppliersRespond updateSuppliers(Long id, SuppliersRequest suppliersRequest) throws Exception;
    void deleteSuppliers(Long id) throws IOException;
}
