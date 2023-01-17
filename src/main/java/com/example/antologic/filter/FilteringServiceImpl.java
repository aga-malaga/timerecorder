package com.example.antologic.filter;

import com.example.antologic.user.User;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class FilteringServiceImpl implements FilteringService {

    private final UserSpecification userSpecification;

    public List<User> filter(List<SearchCriteria> searchCriteria) {

        return new ArrayList<>();
    }


}
