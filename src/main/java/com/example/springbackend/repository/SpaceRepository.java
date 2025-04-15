package com.example.springbackend.repository;

import com.example.springbackend.model.Space;
import com.example.springbackend.model.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    List<Space> findByType(SpaceType type);

    List<Space> findByCapacityGreaterThanEqual(Integer capacity);

    List<Space> findByPricePerHourLessThanEqual(BigDecimal maxPrice);

    List<Space> findByLocation(String location);
}