package com.example.timerecorder.timeRecord.domain;

import com.example.timerecorder.project.Project;
import com.example.timerecorder.projectUser.domain.ProjectUser;
import com.example.timerecorder.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    List<TimeRecord> findTimeRecordsByProject(@Param("project") Project project);

    @Query("SELECT t FROM TimeRecord t " +
            "LEFT join ProjectUser pu ON t.projectUser.user.id = pu.user.id " +
            "LEFT JOIN User u ON pu.user.id  = u.id WHERE u.id = :#{#user.id} " +
            "AND t.start >= :#{#period} AND t.stop <= :#{#now}")
    List<TimeRecord> findTimeRecordsByUser(@Param("user") User user, @Param("period") LocalDateTime period, @Param("now") LocalDateTime now);

    @Query(value =
            "SELECT SUM(extract('hour' from t.stop) - extract('hour' from t.start))" +
                    "from time_records as t " +
                    "WHERE project_user_user_id = :#{#projectUser.user.id} AND " +
                    "project_user_project_id = :#{#projectUser.project.id} " +
                    "AND t.start >= :#{#period} AND t.stop <= :#{#now}", nativeQuery = true)
    Long findTimeRecordsByProjectUserAndSumHours(@Param("projectUser") ProjectUser projectUser,  @Param("period") LocalDateTime period, @Param("now") LocalDateTime now);

    @Query(value =
            "SELECT SUM((extract('hour' from t.stop) - extract('hour' from t.start)) * t.salary)" +
                    "from time_records as t " +
                    "WHERE project_user_user_id = :#{#projectUser.user.id} AND " +
                    "project_user_project_id = :#{#projectUser.project.id} " +
                    "AND t.start >= :#{#period} AND t.stop <= :#{#now}", nativeQuery = true)
    BigDecimal findTimeRecordsByProjectUserAndSumHCost(@Param("projectUser") ProjectUser projectUser,  @Param("period") LocalDateTime period, @Param("now") LocalDateTime now);

    void removeTimeRecordByUuid(UUID recordUuid);

    TimeRecord findTimeRecordByUuid(UUID recordUuid);


}