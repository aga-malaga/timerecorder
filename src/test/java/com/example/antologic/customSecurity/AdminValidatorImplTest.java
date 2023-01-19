package com.example.antologic.customSecurity;

import com.example.antologic.UserTestFactory;
import com.example.antologic.common.UnauthorizedException;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        User admin = UserTestFactory.createUserAdmin();

        given(userRepository.findUserByUuidAndRole(admin.getUuid(), admin.getRole())).willReturn(Optional.of(admin));

        //when
        //then

        assertThat(underTest.validate(admin.getUuid())).isTrue();
    }

    @Test
    void checkIfThrowsWhenUserIsNotAdmin() {
        User user = UserTestFactory.createUserEmployee();

        given(userRepository.findUserByUuidAndRole(user.getUuid(), Role.ADMIN)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.validate(user.getUuid()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User lacks valid authentication credentials for the request");
    }
}