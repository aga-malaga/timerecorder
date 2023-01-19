package com.example.antologic.service;

import com.example.antologic.UserTestFactory;
import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.NoContentException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.customSecurity.AdminValidator;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserCreateForm;
import com.example.antologic.user.dto.UserUpdateForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AdminValidator adminValidator;
    @InjectMocks
    private UserServiceImpl underTest;


    @Test
    void checkIfIsValidating() {
        // given
        final UUID admin = UUID.randomUUID();
        // when
        underTest.validate(admin);
        // then
        verify(adminValidator).validate(admin);
    }

    @Test
    void checkIfCreatesUser() {
        // given
        UserCreateForm form = UserTestFactory.createUserForm();
        form.setLogin("login");
        User user = UserTestFactory.createUserEmployee();
        user.setLogin("login");

        // when
        underTest.createUser(UUID.randomUUID(), form);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        final User userArgumentCaptorValue = userArgumentCaptor.getValue();

        assertThat(userArgumentCaptorValue.getLogin()).isEqualTo(user.getLogin());

    }

    @Test
    void checkIfWillThrowWhenLoginExistsWhileCreatingUser() {
        // given
        UserCreateForm form = UserTestFactory.createUserForm();

        given(userRepository.existsByLogin(form.getLogin()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.createUser(UUID.randomUUID(), form))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("This login already exists");
    }

    @Test
    void checkIfEditsUser() {
        //given
        UUID adminUUID = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        UserUpdateForm form = UserTestFactory.createUpdateUserForm();
        User userTest = new User();

        userTest.setLogin("userTest");
        userTest.setEmail("userTest@wp.pl");

        form.setLogin("marian");
        form.setEmail("marian@wp.pl");
        //when
        when(userRepository.findByUuid(any())).thenReturn(Optional.of(userTest));
        underTest.editUser(adminUUID, form);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(userTest.getLogin()).isEqualTo(capturedUser.getLogin());
        assertThat(userTest.getEmail()).isEqualTo(capturedUser.getEmail());
    }

    @Test
    void checkIfWhileEditingUserCallsInnerMethods() {
        //given
        User admin = UserTestFactory.createUserAdmin();
        UserUpdateForm form = UserTestFactory.createUpdateUserForm();
        User user = UserTestFactory.createUserEmployee();
        //when
        when(userRepository.findByUuid(any())).thenReturn(Optional.of(user));
        underTest.editUser(any(), form);
        //then
        verify(userRepository).findByUuid(any());
        verify(adminValidator).validate(any());
    }

    @Test
    void checkIfThrowsWhenUsersIdNotFoundWhileEditing() {
        //given
        User admin = UserTestFactory.createUserAdmin();
        UserUpdateForm form = UserTestFactory.createUpdateUserForm();
        //then,when
        assertThatThrownBy(() -> underTest.editUser(admin.getUuid(), form))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with id " + form.getUserUuid() + " not found");
    }

    @Test
    void checkIfDeletesUser() {
        //given
        UUID adminUUID = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        //when
        when(userRepository.existsByUuid(userUUID)).thenReturn(true);
        underTest.deleteUser(adminUUID, userUUID);
        //then
        verify(userRepository).removeUserByUuid(userUUID);
    }

    @Test
    void checkIfThrowsWhenUsersIdNotFoundWhileDeleting() {
        //given
        UUID adminUUID = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        //when
        when(userRepository.existsByUuid(userUUID)).thenReturn(false);
        assertThatThrownBy(() -> underTest.deleteUser(adminUUID, userUUID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with id " + userUUID + " not found");
    }

    @Test
    void checkIfThrowsWhenNoCriteriaWhileFilteringUsers() {
        //given
        UUID adminUUID = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        SearchCriteria criteria = null;
        PageRequest page = PageRequest.of(0, 6);

        //when,then
        assertThatThrownBy(() -> underTest.filterUsers(adminUUID, criteria, page))
                .isInstanceOf(NoContentException.class)
                .hasMessageContaining("No criteria included");
    }
}