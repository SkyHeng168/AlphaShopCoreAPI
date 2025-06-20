package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRequest;
import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRespond;

import java.util.List;

public interface IWareHouseService{
    WareHouseRespond createWareHouse(WareHouseRequest wareHouseRequest);
    List<WareHouseRespond> getAllWareHouse();
    WareHouseRespond updateWareHouse(Long id, WareHouseRequest wareHouseRequest);
    void deleteWareHouse(Long id);
}
