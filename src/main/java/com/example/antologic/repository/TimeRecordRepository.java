package com.example.antologic.repository;

import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.timeRecord.TimeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public
interface TimeRecordRepository extends JpaRepository<TimeRecord, Long>,
        JpaSpecificationExecutor<TimeRecord> {

    @Query("SELECT t from TimeRecord t where t.start >= ?1 AND t.stop <= ?2")
    List<TimeRecord> findTimeRecordBetween(LocalDateTime begin, LocalDateTime end);
    @EntityGraph(attributePaths = "projectUser.project")
    Page<TimeRecord> findAllByProjectUser (ProjectUser projectUser, Pageable p);

    TimeRecord findTimeRecordByUuid(UUID recordUuid);

    void removeTimeRecordByUuid(UUID recordUuid);
}
