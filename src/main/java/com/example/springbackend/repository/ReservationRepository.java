package com.example.springbackend.repository;

import com.example.springbackend.model.Reservation;
import com.example.springbackend.model.ReservationStatus;
import com.example.springbackend.model.Space;
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

        boolean existsBySpaceAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        Space space, ReservationStatus status, LocalDateTime startTime, LocalDateTime endTime);

        @Query("SELECT MIN(r.endTime) FROM Reservation r " +
                        "WHERE r.space = :space " +
                        "AND r.status = :status " +
                        "AND r.endTime > :currentTime " +
                        "AND NOT EXISTS (" +
                        "    SELECT 1 FROM Reservation r2 " +
                        "    WHERE r2.space = :space " +
                        "    AND r2.status = :status " +
                        "    AND r2.startTime <= r.endTime " +
                        "    AND r2.endTime > r.endTime" +
                        ")")
        LocalDateTime findNextAvailableTime(
                        @Param("space") Space space,
                        @Param("currentTime") LocalDateTime currentTime,
                        @Param("status") ReservationStatus status);
}