package com.example.antologic.project;

import com.example.antologic.filter.ProjectSearchCriteria;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProjectSpecification implements Specification<Project> {

    private final ProjectSearchCriteria projectSearchCriteria;

    @Override
    public Predicate toPredicate(final Root<Project> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (projectSearchCriteria.name() != null) {
            predicates.add(builder.like(
                    builder.lower(
                            root.<String>get(
                                    "name")
                    ), builder.lower(
                            builder.literal(
                                    "%" + projectSearchCriteria.name() + "%"))));
        }
        if (projectSearchCriteria.start() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.<LocalDateTime>get("start"), projectSearchCriteria.start()));
        }
        if (projectSearchCriteria.stop() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.<LocalDateTime>get("stop"), projectSearchCriteria.stop()));
        }
        if (projectSearchCriteria.userUuid() != null && projectSearchCriteria.userUuid().size() > 0) {
            projectSearchCriteria.userUuid().forEach(uuid -> {
                predicates.add(
                        builder.isTrue(
                                root.join(Project.Fields.users)
                                        .get(ProjectUser.Fields.user).get(User.Fields.uuid).in(uuid)
                        )
                );
            });
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }


}
