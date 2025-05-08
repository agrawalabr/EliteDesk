package com.example.springbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailabilityRequest {
    @NotNull(message = "Space ID is required")
    private Long spaceId;

    @NotNull(message = "Date is required")
    private LocalDate date;
}