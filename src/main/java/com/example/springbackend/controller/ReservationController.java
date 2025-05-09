package com.example.springbackend.controller;

import com.example.springbackend.dto.ApiResponse;
import com.example.springbackend.dto.CategorizedReservationsResponse;
import com.example.springbackend.dto.ReservationEmailRequest;
import com.example.springbackend.dto.ReservationRequest;
import com.example.springbackend.dto.ReservationResponse;
import com.example.springbackend.dto.TimeSlot;
import com.example.springbackend.dto.AvailabilityRequest;
import com.example.springbackend.model.Reservation;
import com.example.springbackend.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @Valid @RequestBody ReservationRequest request) {
        ReservationResponse reservation = reservationService.createReservation(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(reservation, "Reservation created successfully"));
    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservationWithEmail(
            @Valid @RequestBody ReservationEmailRequest request) {
        ReservationResponse reservation = reservationService.createReservationWithEmail(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(reservation, "Reservation created successfully"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<ReservationResponse>> cancelReservation(
            @PathVariable Long id,
            @RequestParam String userEmail) {
        ReservationResponse cancelledReservation = reservationService.cancelReservation(id, userEmail);
        return ResponseEntity.ok(ApiResponse.success(cancelledReservation, "Reservation cancelled successfully"));
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getReservationsBySpaceId(@PathVariable Long spaceId) {
        List<ReservationResponse> reservations = reservationService.getReservationsBySpaceId(spaceId);
        return ResponseEntity.ok(ApiResponse.success(reservations, "Reservations retrieved successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CategorizedReservationsResponse>> getReservationsByUserId(
            @PathVariable Long userId) {
        CategorizedReservationsResponse reservations = reservationService.getCategorizedReservationsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(reservations, "User reservations retrieved successfully"));
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getReservationsByUserEmail(
            @PathVariable String email) {
        List<ReservationResponse> reservations = reservationService.getReservationsByUserEmail(email);
        return ResponseEntity.ok(ApiResponse.success(reservations, "User reservations retrieved successfully"));
    }

    @GetMapping("/user/email/{email}/categorized")
    public ResponseEntity<ApiResponse<CategorizedReservationsResponse>> getCategorizedReservationsByUserEmail(
            @PathVariable String email) {
        CategorizedReservationsResponse reservations = reservationService.getCategorizedReservationsByUserEmail(email);
        return ResponseEntity.ok(ApiResponse.success(reservations, "User reservations retrieved successfully"));
    }

    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<List<TimeSlot>>> getAvailableTimeSlots(
            @Valid @RequestParam Long spaceId,
            @Valid @RequestParam String date) {
        AvailabilityRequest request = new AvailabilityRequest();
        request.setSpaceId(spaceId);
        request.setDate(java.time.LocalDate.parse(date));

        List<TimeSlot> timeSlots = reservationService.getAvailableTimeSlots(request);
        return ResponseEntity.ok(ApiResponse.success(timeSlots, "Available time slots retrieved successfully"));
    }
}