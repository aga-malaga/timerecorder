package com.example.antologic.repository;

import com.example.antologic.timeRecord.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public
interface TimeRecordRepository extends JpaRepository<TimeRecord, Long>,
        JpaSpecificationExecutor<TimeRecord> {
}
