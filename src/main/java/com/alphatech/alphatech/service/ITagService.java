package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.TagDto.TagRespond;

import java.util.List;

public interface ITagService {
    List<TagRespond> findAll();
}
