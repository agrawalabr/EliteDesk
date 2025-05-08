package com.example.springbackend.dto;

import com.example.springbackend.model.ReservationStatus;
import com.example.springbackend.model.SpaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long spaceId;
    private String spaceName;
    private String spaceLocation;
    private SpaceType spaceType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
}