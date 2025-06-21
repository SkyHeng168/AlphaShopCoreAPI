package com.alphatech.alphatech.dto.TagDto;

import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.model.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public record TagRespond(
        Long id,
        String tagName,
        String slug,
        Set<ProductRespond> products
) {
    public static TagRespond convertToDto(Tag tag) {
        return new TagRespond(
                tag.getId(),
                tag.getTagName(),
                tag.getSlug(),
                tag.getProducts() != null
                        ? tag.getProducts().stream()
                        .map(ProductRespond::convertEntityToDto)
                        .collect(Collectors.toSet())
                        : Set.of()
        );
    }
}
