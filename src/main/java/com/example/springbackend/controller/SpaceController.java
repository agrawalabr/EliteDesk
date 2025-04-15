package com.example.springbackend.controller;

import com.example.springbackend.dto.SpaceRequest;
import com.example.springbackend.model.Space;
import com.example.springbackend.service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<Space> createSpace(@Valid @RequestBody SpaceRequest request) {
        Space createdSpace = spaceService.createSpace(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSpace.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdSpace);
    }
}