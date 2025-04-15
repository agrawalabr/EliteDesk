package com.example.springbackend.service;

import com.example.springbackend.dto.ReservationRequest;
import com.example.springbackend.model.Reservation;
import com.example.springbackend.model.ReservationStatus;
import com.example.springbackend.model.Space;
import com.example.springbackend.model.User;
import com.example.springbackend.repository.ReservationRepository;
import com.example.springbackend.repository.SpaceRepository;
import com.example.springbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        // Validate that the space exists
        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new EntityNotFoundException("Space not found"));

        // Validate that the user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check for overlapping reservations
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                request.getSpaceId(),
                request.getStartTime(),
                request.getEndTime());

        if (!overlappingReservations.isEmpty()) {
            throw new IllegalStateException("The space is already reserved for the requested time period");
        }

        // Create and save the reservation
        Reservation reservation = new Reservation();
        reservation.setSpace(space);
        reservation.setUser(user);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        return reservationRepository.save(reservation);
    }
}