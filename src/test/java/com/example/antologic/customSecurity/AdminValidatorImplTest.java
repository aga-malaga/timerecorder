package com.example.antologic.customSecurity;

import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminValidatorImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AdminValidatorImpl underTest;

    @Test
    void validateUserAdmin() {
        //given

        User admin = new User("9f05e7e3-6382-482e-a40d-78074569aed2",
                "admin2",
                "admin2",
                Role.ADMIN,
                "admin2",
                "pass",
                BigDecimal.valueOf(100));

        given(userRepository.findUserByUuidAndRole(admin.getUuid(), admin.getRole())).willReturn(Optional.of(admin));

        //when
        //then

        assertThat(underTest.validate(admin.getUuid())).isTrue();
    }

    @Test
    void checkIfThrowsWhenUserIsNotAdmin() {
        User user = new User("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0",
                "employee",
                "employee",
                Role.EMPLOYEE,
                "employee@wp.pl",
                "pass",
                BigDecimal.valueOf(10));

        given(userRepository.findUserByUuidAndRole(user.getUuid(), Role.ADMIN)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.validate(user.getUuid()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User lacks valid authentication credentials for the request");
    }
}