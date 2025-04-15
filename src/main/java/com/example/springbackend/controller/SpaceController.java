package com.example.springbackend.controller;

import com.example.springbackend.dto.SpaceRequest;
import com.example.springbackend.dto.SpaceResponse;
import com.example.springbackend.model.Space;
import com.example.springbackend.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public ResponseEntity<Space> createSpace(@Valid @RequestBody SpaceRequest request) {
        Space space = spaceService.createSpace(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(space.getId())
                .toUri();

        return ResponseEntity.created(location).body(space);
    }

    @GetMapping
    public ResponseEntity<List<SpaceResponse>> getAllSpaces() {
        List<SpaceResponse> spaces = spaceService.getAllSpaces();
        return ResponseEntity.ok(spaces);
    }
}