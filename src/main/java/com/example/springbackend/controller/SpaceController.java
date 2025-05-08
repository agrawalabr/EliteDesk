package com.example.springbackend.controller;

import com.example.springbackend.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<SpaceResponse>> createSpace(@Valid @RequestBody SpaceRequest request) {
        Space space = spaceService.createSpace(request);
        SpaceResponse response = SpaceResponse.fromSpace(
                space,
                false, // A newly created space is not reserved
                null // No next available time since it's not reserved
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(space.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(response, "Space created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpaceResponse>>> getAllSpaces() {
        List<SpaceResponse> spaces = spaceService.getAllSpaces();
        return ResponseEntity.ok(ApiResponse.success(spaces, "Spaces retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpaceResponse>> getSpaceById(@PathVariable Long id) {
        SpaceResponse space = spaceService.getSpaceById(id);
        return ResponseEntity.ok(ApiResponse.success(space, "Space retrieved successfully"));
    }
}