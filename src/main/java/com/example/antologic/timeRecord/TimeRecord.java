package com.example.antologic.timeRecord;

import com.example.antologic.projectUser.ProjectUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_records")
public class TimeRecord {

    @Column(name = "uuid")
    private final UUID uuid = UUID.randomUUID();
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "start")
    private LocalDateTime start;
    @Column(name = "stop")
    private LocalDateTime stop;
    @Column(name = "salary")
    private BigDecimal salary;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectUser projectUser;
}
