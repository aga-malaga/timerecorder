package com.example.antologic.user;

import com.example.antologic.filter.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (searchCriteria.name() != null) {
            predicates.add(builder.like(
                    builder.lower(
                            root.<String>get(
                                    "name")
                    ), builder.lower(
                            builder.literal(
                                    "%" + searchCriteria.name() + "%"))));
        }
        if (searchCriteria.surname() != null) {
            predicates.add(builder.like(
                    builder.lower(
                            root.<String>get(
                                    "surname")
                    ), builder.lower(
                            builder.literal(
                                    "%" + searchCriteria.surname() + "%"))));
        }
        if (searchCriteria.costFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("costPerHour"), searchCriteria.costFrom()));
        }
        if (searchCriteria.costTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("costPerHour"), searchCriteria.costTo()));
        }
        if (searchCriteria.role() != null) {
            predicates.add(builder.equal(root.get("role"), searchCriteria.role()));
        }

        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
