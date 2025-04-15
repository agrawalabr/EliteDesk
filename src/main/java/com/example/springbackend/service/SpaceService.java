package com.example.springbackend.service;

import com.example.springbackend.dto.SpaceRequest;
import com.example.springbackend.model.Space;
import com.example.springbackend.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    @Transactional
    public Space createSpace(SpaceRequest request) {
        Space space = new Space(
                request.getName(),
                request.getLocation(),
                request.getType(),
                request.getCapacity(),
                request.getPricePerHour());

        return spaceRepository.save(space);
    }
}