package com.example.antologic.projectUser;

import com.example.antologic.project.Project;
import com.example.antologic.timeRecord.TimeRecord;
import com.example.antologic.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity(name = "ProjectUser")
@Table(name = "project_user")
public class ProjectUser {

    @OneToMany(
            mappedBy = "projectUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    List<TimeRecord> timeRecords = new ArrayList<>();
    @EmbeddedId
    private ProjectUserId id;
    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;
    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
    @Column(name = "enter_on")
    private LocalDateTime enterOn;
    @Column(name = "leave_on")
    private LocalDateTime leaveOn;

    public ProjectUser(final Project project, final User user, final LocalDateTime enter, final LocalDateTime leave) {
        this.project = project;
        this.user = user;
        this.id = new ProjectUserId(project.getId(), user.getId());
        this.enterOn = enter;
        this.leaveOn = leave;
    }
}

