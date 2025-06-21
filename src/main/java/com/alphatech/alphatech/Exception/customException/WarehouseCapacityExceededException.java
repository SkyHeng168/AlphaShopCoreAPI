package com.alphatech.alphatech.Exception.customException;

public class WarehouseCapacityExceededException extends RuntimeException {
    public WarehouseCapacityExceededException(String message) {
        super(message);
    }
}
