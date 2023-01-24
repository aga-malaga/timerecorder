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
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project {

    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private final UUID uuid = UUID.randomUUID();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

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
}


