package com.example.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorizedReservationsResponse {
    private List<ReservationResponse> upcomingReservations;
    private List<ReservationResponse> pastReservations;
}