package com.example.antologic.projectUser;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@Table(name = "project_user_id")
public class ProjectUserId implements Serializable {
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "user_id")
    private Long userId;

    public ProjectUserId(){}

    public ProjectUserId(
            Long projectId,
            Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProjectUserId that = (ProjectUserId) o;
        return projectId.equals(that.projectId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
