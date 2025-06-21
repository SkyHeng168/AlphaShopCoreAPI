package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.TagDto.TagRespond;
import com.alphatech.alphatech.model.Tag;
import com.alphatech.alphatech.repository.TagRepository;
import com.alphatech.alphatech.service.ITagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TagService implements ITagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagRespond> findAll() {
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("Tag Not Found");
        }
        return tags.stream()
                .map(TagRespond::convertToDto)
                .collect(Collectors.toList());
    }
}
