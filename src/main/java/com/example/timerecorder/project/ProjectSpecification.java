package com.example.timerecorder.project;

import com.example.timerecorder.project.dto.ProjectSearchCriteria;
import com.example.timerecorder.project.dto.ProjectDtoBudget;
import com.example.timerecorder.projectUser.domain.ProjectUser;
import com.example.timerecorder.user.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProjectSpecification implements Specification<Project> {

    private final ProjectSearchCriteria projectSearchCriteria;

    private final List<ProjectDtoBudget> projectDtoBudgets;

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
        if (Objects.nonNull(projectSearchCriteria.budget())) {
            List<String> nameList;
            if (projectSearchCriteria.budget()) {
                nameList = projectDtoBudgets.stream().filter(p -> p.budgetPercent().compareTo(BigDecimal.valueOf(100)) > 0)
                        .map(ProjectDtoBudget::name).collect(Collectors.toList());
            } else {
                nameList = projectDtoBudgets.stream().filter(p -> p.budgetPercent().compareTo(BigDecimal.valueOf(100)) <= 0)
                        .map(ProjectDtoBudget::name).collect(Collectors.toList());
            }
            predicates.add(builder.isTrue(root.get(Project.Fields.name).in(nameList)));
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
