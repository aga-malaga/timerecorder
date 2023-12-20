package com.example.timerecorder.user.domain;

import com.example.timerecorder.project.dto.SearchCriteria;
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
public class UserSpecification implements Specification<User> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(searchCriteria.name())) {
            predicates.add(builder.like(
                    builder.lower(
                            root.<String>get(
                                    "name")
                    ), builder.lower(
                            builder.literal(
                                    "%" + searchCriteria.name() + "%"))));
        }
        if (Objects.nonNull(searchCriteria.surname())) {
            predicates.add(builder.like(
                    builder.lower(
                            root.<String>get(
                                    "surname")
                    ), builder.lower(
                            builder.literal(
                                    "%" + searchCriteria.surname() + "%"))));
        }
        if (Objects.nonNull(searchCriteria.costFrom())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("costPerHour"), searchCriteria.costFrom()));
        }
        if (Objects.nonNull(searchCriteria.costTo())) {
            predicates.add(builder.lessThanOrEqualTo(root.get("costPerHour"), searchCriteria.costTo()));
        }
        if (Objects.nonNull(searchCriteria.role())) {
            predicates.add(builder.equal(root.get("role"), searchCriteria.role()));
        }

        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
