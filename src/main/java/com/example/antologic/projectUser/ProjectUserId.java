package com.example.antologic.projectUser;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "project_user_id")
public class ProjectUserId implements Serializable {
    @EqualsAndHashCode.Include
    @Column(name = "project_id")
    private Long projectId;
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long userId;

    public ProjectUserId() {
    }

    public ProjectUserId(
            Long projectId,
            Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
}
