package com.example.antologic.filter;

import com.example.antologic.user.User;

import java.util.List;

public interface FilteringService {

    List<User> filter(List<SearchCriteria> searchCriteriaList);
}
