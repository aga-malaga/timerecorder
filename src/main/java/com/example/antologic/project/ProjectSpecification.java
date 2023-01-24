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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ProjectSpecification implements Specification<Project> {

    private final ProjectSearchCriteria projectSearchCriteria;

    @Override
    public Predicate toPredicate(final Root<Project> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(projectSearchCriteria.name())) {
            predicates.add(builder.like(
                    builder.lower(
                            root.get(
                                    Project.Fields.name)
                    ), builder.lower(
                            builder.literal(
                                    "%" + projectSearchCriteria.name() + "%"))));
        }
        if (Objects.nonNull(projectSearchCriteria.start())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Project.Fields.startDate), projectSearchCriteria.start()));
        }
        if (Objects.nonNull(projectSearchCriteria.stop())) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Project.Fields.endDate), projectSearchCriteria.stop()));
        }
        if (Objects.nonNull(projectSearchCriteria.userUuid()) && projectSearchCriteria.userUuid().size() > 0) {
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
