package com.example.springbackend.dto;

import com.example.springbackend.model.Space;
import com.example.springbackend.model.SpaceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SpaceResponse {
    private Long id;
    private String name;
    private String location;
    private SpaceType type;
    private int capacity;
    private double pricePerHour;
    private boolean isCurrentlyReserved;
    private LocalDateTime nextAvailableTime;

    public static SpaceResponse fromSpace(Space space, boolean isCurrentlyReserved, LocalDateTime nextAvailableTime) {
        SpaceResponse response = new SpaceResponse();
        response.setId(space.getId());
        response.setName(space.getName());
        response.setLocation(space.getLocation());
        response.setType(space.getType());
        response.setCapacity(space.getCapacity());
        response.setPricePerHour(space.getPricePerHour().doubleValue());
        response.setCurrentlyReserved(isCurrentlyReserved);
        response.setNextAvailableTime(nextAvailableTime);
        return response;
    }
}