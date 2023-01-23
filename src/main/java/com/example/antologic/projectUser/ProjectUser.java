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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "ProjectUser")
@Table(name = "project_user")
@NoArgsConstructor
@FieldNameConstants
@Getter
@Setter
public class ProjectUser {

    @EmbeddedId
    private ProjectUserId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
    @Column(name = "enter_on")
    private LocalDateTime enterOn;
    @Column(name = "leave_on")
    private LocalDateTime leaveOn;
    @OneToMany(
            mappedBy = "projectUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    List<TimeRecord> timeRecords = new ArrayList<>();

    public ProjectUser(final Project project, final User user, final LocalDateTime enter, final LocalDateTime leave) {
        this.project = project;
        this.user = user;
        this.id = new ProjectUserId(project.getId(), user.getId());
        this.enterOn = enter;
        this.leaveOn = leave;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProjectUser that = (ProjectUser) o;
        return project.equals(that.project) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, user);
    }
}


