package com.alphatech.alphatech.dto;

public record ErrorResponse(
        String message,
        String timestamp,
        int status
) {
}