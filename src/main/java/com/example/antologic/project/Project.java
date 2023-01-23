package com.example.antologic.project;

import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private final UUID uuid = UUID.randomUUID();
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "stop")
    private LocalDateTime stop;

    @Column(name = "budget")
    private BigDecimal budget;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<ProjectUser> users = new ArrayList<>();

    public void addUser(User user, LocalDateTime enter, LocalDateTime leave) {
        ProjectUser projectUser = new ProjectUser(this, user, enter, leave);
        users.add(projectUser);
        user.getProjects().add(projectUser);
    }

    public void removeUser(User user) {
        for (Iterator<ProjectUser> iterator = users.iterator();
             iterator.hasNext(); ) {
            ProjectUser projectUser = iterator.next();

            if (projectUser.getProject().equals(this) &&
                    projectUser.getUser().equals(user)) {
                iterator.remove();
                projectUser.getUser().getProjects().remove(projectUser);
                users.remove(projectUser);
                projectUser.setProject(null);
                projectUser.setUser(null);
            }
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Project project = (Project) o;
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


