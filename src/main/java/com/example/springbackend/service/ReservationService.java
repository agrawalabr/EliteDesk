package com.example.springbackend.service;

import com.example.springbackend.dto.ReservationEmailRequest;
import com.example.springbackend.dto.ReservationRequest;
import com.example.springbackend.dto.ReservationResponse;
import com.example.springbackend.dto.TimeSlot;
import com.example.springbackend.dto.AvailabilityRequest;
import com.example.springbackend.model.Reservation;
import com.example.springbackend.model.ReservationStatus;
import com.example.springbackend.model.Space;
import com.example.springbackend.model.User;
import com.example.springbackend.repository.ReservationRepository;
import com.example.springbackend.repository.SpaceRepository;
import com.example.springbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

        private final ReservationRepository reservationRepository;
        private final SpaceRepository spaceRepository;
        private final UserRepository userRepository;

        @Transactional
        public ReservationResponse createReservation(ReservationRequest request) {
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

                reservation = reservationRepository.save(reservation);

                return new ReservationResponse(
                                reservation.getId(),
                                reservation.getUser().getId(),
                                reservation.getUser().getName(),
                                reservation.getSpace().getId(),
                                reservation.getSpace().getName(),
                                reservation.getSpace().getLocation(),
                                reservation.getSpace().getType(),
                                reservation.getStartTime(),
                                reservation.getEndTime(),
                                reservation.getStatus());
        }

        @Transactional
        public ReservationResponse createReservationWithEmail(ReservationEmailRequest request) {
                log.info("Creating reservation for email: {}, spaceId: {}, startTime: {}, endTime: {}",
                                request.getUserId(), request.getSpaceId(), request.getStartTime(),
                                request.getEndTime());

                if (request.getEndTime().isBefore(request.getStartTime())) {
                        throw new IllegalArgumentException("End time cannot be before start time");
                }

                if (request.getStartTime().isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException("Start time cannot be in the past");
                }

                // Validate that the space exists
                Space space = spaceRepository.findById(request.getSpaceId())
                                .orElseThrow(() -> {
                                        log.error("Space not found with ID: {}", request.getSpaceId());
                                        return new EntityNotFoundException(
                                                        "Space not found with ID: " + request.getSpaceId());
                                });

                // Find user by email
                User user = userRepository.findByEmail(request.getUserId())
                                .orElseThrow(() -> {
                                        log.error("User not found with email: {}", request.getUserId());
                                        return new EntityNotFoundException(
                                                        "User not found with email: " + request.getUserId());
                                });

                // Check for overlapping reservations
                List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                                request.getSpaceId(),
                                request.getStartTime(),
                                request.getEndTime());

                if (!overlappingReservations.isEmpty()) {
                        log.warn("Overlapping reservation found for space: {}, startTime: {}, endTime: {}",
                                        request.getSpaceId(), request.getStartTime(), request.getEndTime());
                        throw new IllegalStateException("The space is already reserved for the requested time period");
                }

                try {
                        // Create and save the reservation
                        Reservation reservation = new Reservation();
                        reservation.setSpace(space);
                        reservation.setUser(user);
                        reservation.setStartTime(request.getStartTime());
                        reservation.setEndTime(request.getEndTime());
                        reservation.setStatus(ReservationStatus.CONFIRMED);

                        reservation = reservationRepository.save(reservation);
                        log.info("Successfully created reservation with ID: {}", reservation.getId());

                        return new ReservationResponse(
                                        reservation.getId(),
                                        reservation.getUser().getId(),
                                        reservation.getUser().getName(),
                                        reservation.getSpace().getId(),
                                        reservation.getSpace().getName(),
                                        reservation.getSpace().getLocation(),
                                        reservation.getSpace().getType(),
                                        reservation.getStartTime(),
                                        reservation.getEndTime(),
                                        reservation.getStatus());
                } catch (Exception e) {
                        log.error("Error creating reservation: {}", e.getMessage(), e);
                        throw new RuntimeException("Failed to create reservation: " + e.getMessage());
                }
        }

        public List<ReservationResponse> getReservationsBySpaceId(Long spaceId) {
                // Validate that the space exists
                if (!spaceRepository.existsById(spaceId)) {
                        throw new EntityNotFoundException("Space not found");
                }

                return reservationRepository.findBySpaceId(spaceId).stream()
                                .map(reservation -> new ReservationResponse(
                                                reservation.getId(),
                                                reservation.getUser().getId(),
                                                reservation.getUser().getName(),
                                                reservation.getSpace().getId(),
                                                reservation.getSpace().getName(),
                                                reservation.getSpace().getLocation(),
                                                reservation.getSpace().getType(),
                                                reservation.getStartTime(),
                                                reservation.getEndTime(),
                                                reservation.getStatus()))
                                .collect(Collectors.toList());
        }

        public List<ReservationResponse> getReservationsByUserId(Long userId) {
                // Validate that the user exists
                if (!userRepository.existsById(userId)) {
                        throw new EntityNotFoundException("User not found");
                }

                return reservationRepository.findByUserId(userId).stream()
                                .map(reservation -> new ReservationResponse(
                                                reservation.getId(),
                                                reservation.getUser().getId(),
                                                reservation.getUser().getName(),
                                                reservation.getSpace().getId(),
                                                reservation.getSpace().getName(),
                                                reservation.getSpace().getLocation(),
                                                reservation.getSpace().getType(),
                                                reservation.getStartTime(),
                                                reservation.getEndTime(),
                                                reservation.getStatus()))
                                .collect(Collectors.toList());
        }

        public List<ReservationResponse> getReservationsByUserEmail(String email) {
                // Find user by email
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

                return reservationRepository.findByUserId(user.getId()).stream()
                                .map(reservation -> new ReservationResponse(
                                                reservation.getId(),
                                                reservation.getUser().getId(),
                                                reservation.getUser().getName(),
                                                reservation.getSpace().getId(),
                                                reservation.getSpace().getName(),
                                                reservation.getSpace().getLocation(),
                                                reservation.getSpace().getType(),
                                                reservation.getStartTime(),
                                                reservation.getEndTime(),
                                                reservation.getStatus()))
                                .collect(Collectors.toList());
        }

        public List<TimeSlot> getAvailableTimeSlots(AvailabilityRequest request) {
                log.info("Getting available time slots for space {} on date {}", request.getSpaceId(),
                                request.getDate());

                // Validate that the space exists
                Space space = spaceRepository.findById(request.getSpaceId())
                                .orElseThrow(() -> new EntityNotFoundException("Space not found"));

                // Define business hours (9:00 AM to 5:00 PM)
                LocalTime startBusinessHours = LocalTime.of(9, 0);
                LocalTime endBusinessHours = LocalTime.of(17, 0);
                Duration slotDuration = Duration.ofMinutes(30);

                // Get all reservations for the specified date
                LocalDateTime startOfDay = request.getDate().atStartOfDay();
                LocalDateTime endOfDay = request.getDate().atTime(LocalTime.MAX);
                List<Reservation> existingReservations = reservationRepository
                                .findBySpaceIdAndStartTimeBetween(request.getSpaceId(), startOfDay, endOfDay);

                log.info("Found {} existing reservations for the date", existingReservations.size());
                existingReservations.forEach(reservation -> log.info("Existing reservation: {} to {}",
                                reservation.getStartTime(),
                                reservation.getEndTime()));

                List<TimeSlot> timeSlots = new ArrayList<>();
                LocalTime currentTime = startBusinessHours;

                // Get current date and time
                LocalDateTime now = LocalDateTime.now();
                LocalDate today = now.toLocalDate();

                // Generate time slots
                while (currentTime.plusMinutes(30).isAfter(currentTime) &&
                                !currentTime.isAfter(endBusinessHours.minusMinutes(30))) {
                        LocalTime slotEndTime = currentTime.plus(slotDuration);

                        // Check if the slot overlaps with any existing reservations
                        LocalDateTime slotStart = request.getDate().atTime(currentTime);
                        LocalDateTime slotEnd = request.getDate().atTime(slotEndTime);

                        boolean isAvailable = true;

                        // Check each reservation for overlap
                        for (Reservation reservation : existingReservations) {
                                // A slot is unavailable only if it actually overlaps with a reservation
                                // This means the slot starts before the reservation ends AND
                                // the slot ends after the reservation starts
                                if (slotStart.isBefore(reservation.getEndTime()) &&
                                                slotEnd.isAfter(reservation.getStartTime())) {
                                        isAvailable = false;
                                        log.debug("Slot {} to {} is unavailable due to reservation from {} to {}",
                                                        currentTime, slotEndTime,
                                                        reservation.getStartTime().toLocalTime(),
                                                        reservation.getEndTime().toLocalTime());
                                        break;
                                }
                        }

                        // Only mark as unavailable if the slot is in the past
                        if (request.getDate().equals(today)) {
                                // If it's today, check against current time
                                isAvailable = isAvailable && slotStart.isAfter(now);
                        } else if (request.getDate().isBefore(today)) {
                                // If it's a past date, all slots are unavailable
                                isAvailable = false;
                        }
                        // If it's a future date, keep the availability as is (based on existing
                        // reservations)

                        log.debug("Time slot {} to {} is {}",
                                        currentTime,
                                        slotEndTime,
                                        isAvailable ? "available" : "unavailable");

                        timeSlots.add(new TimeSlot(currentTime, slotEndTime, isAvailable));
                        currentTime = slotEndTime;
                }

                return timeSlots;
        }
}