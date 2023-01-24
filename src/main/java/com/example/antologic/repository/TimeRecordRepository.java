package com.example.antologic.repository;

import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.timeRecord.TimeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public
interface TimeRecordRepository extends JpaRepository<TimeRecord, Long>,
        JpaSpecificationExecutor<TimeRecord> {

    @Query("""
            SELECT (COUNT(t) > 0) FROM TimeRecord t
            WHERE (t.stop > ?1 AND t.stop <= ?2) OR (t.start >= ?1 AND t.start < ?2)  OR (t.start < ?1 AND t.stop > ?2) AND t.projectUser.user.uuid = ?3""")
    boolean existTimeRecordBetween(LocalDateTime begin, LocalDateTime end, UUID userUuid);

    @EntityGraph(attributePaths = "projectUser.project")
    Page<TimeRecord> findAllByProjectUser(ProjectUser projectUser, Pageable p);

    @Query("SELECT t FROM TimeRecord t " +
            "LEFT join ProjectUser pu ON t.projectUser.project.id = pu.project.id " +
            "LEFT JOIN Project p ON pu.project.id  = p.id WHERE p.id = :#{#project.id}")
    List<TimeRecord> findTimeRecordsByProject(@Param("project")Project project);

    void removeTimeRecordByUuid(UUID recordUuid);


}