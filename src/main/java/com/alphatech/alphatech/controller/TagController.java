package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.TagDto.TagRespond;
import com.alphatech.alphatech.service.impl.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagRespond>> findAll() {
        List<TagRespond> tagResponds = tagService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tagResponds);
    }
}
