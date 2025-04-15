package com.example.springbackend.repository;

import com.example.springbackend.model.Reservation;
import com.example.springbackend.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findBySpaceId(Long spaceId);

    List<Reservation> findByStatus(ReservationStatus status);

    @Query("SELECT r FROM Reservation r WHERE r.space.id = :spaceId " +
            "AND ((r.startTime BETWEEN :start AND :end) " +
            "OR (r.endTime BETWEEN :start AND :end) " +
            "OR (:start BETWEEN r.startTime AND r.endTime))")
    List<Reservation> findOverlappingReservations(
            @Param("spaceId") Long spaceId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    List<Reservation> findBySpaceIdAndStartTimeBetween(
            Long spaceId,
            LocalDateTime start,
            LocalDateTime end);
}