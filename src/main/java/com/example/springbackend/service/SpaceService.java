package com.example.springbackend.service;

import com.example.springbackend.dto.SpaceRequest;
import com.example.springbackend.dto.SpaceResponse;
import com.example.springbackend.model.ReservationStatus;
import com.example.springbackend.model.Space;
import com.example.springbackend.repository.ReservationRepository;
import com.example.springbackend.repository.SpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {

        private final SpaceRepository spaceRepository;
        private final ReservationRepository reservationRepository;

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

        public List<SpaceResponse> getAllSpaces() {
                LocalDateTime now = LocalDateTime.now();
                return spaceRepository.findAll().stream()
                                .map(space -> {
                                        boolean isReserved = reservationRepository
                                                        .existsBySpaceAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                                                                        space, ReservationStatus.CONFIRMED, now, now);
                                        LocalDateTime nextAvailable = reservationRepository.findNextAvailableTime(
                                                        space, now, ReservationStatus.CONFIRMED);
                                        return SpaceResponse.fromSpace(space, isReserved, nextAvailable);
                                })
                                .collect(Collectors.toList());
        }

        public SpaceResponse getSpaceById(Long id) {
                Space space = spaceRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Space not found"));

                LocalDateTime now = LocalDateTime.now();
                boolean isReserved = reservationRepository
                                .existsBySpaceAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                                                space, ReservationStatus.CONFIRMED, now, now);
                LocalDateTime nextAvailable = reservationRepository.findNextAvailableTime(
                                space, now, ReservationStatus.CONFIRMED);

                return SpaceResponse.fromSpace(space, isReserved, nextAvailable);
        }
}