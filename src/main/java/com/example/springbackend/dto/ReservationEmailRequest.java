package com.example.springbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationEmailRequest {
    @NotNull(message = "Space ID is required")
    private Long spaceId;

    @NotNull(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userId; // This will contain the email

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be current or future time")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @FutureOrPresent(message = "End time must be current or future time")
    private LocalDateTime endTime;
}