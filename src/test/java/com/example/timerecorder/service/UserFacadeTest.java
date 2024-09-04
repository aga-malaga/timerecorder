package com.example.timerecorder.service;

import com.example.timerecorder.UserTestFactory;
import com.example.timerecorder.common.exception.AlreadyExistsException;
import com.example.timerecorder.common.exception.NoContentException;
import com.example.timerecorder.common.exception.NotFoundException;
import com.example.timerecorder.project.dto.SearchCriteria;
import com.example.timerecorder.user.UserFacade;
import com.example.timerecorder.user.domain.User;
import com.example.timerecorder.user.domain.UserRepository;
import com.example.timerecorder.user.domain.UserServiceImpl;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserUpdateForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserFacadeTest {

    private final UserRepository userRepository = mock();
    private final PasswordEncoder passwordEncoder = mock();
    private final UserFacade systemUnderTest = new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    void checkIfCreatesUser() {
        // given
        UserCreateForm form = UserTestFactory.createUserForm();
        form.setLogin("login");
        User user = UserTestFactory.createUserEmployee();
        user.setLogin("login");

        // when
        systemUnderTest.createUser(form);
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
        assertThatThrownBy(() -> systemUnderTest.createUser(form))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("This login already exists");
    }

    @Test
    void checkIfEditsUser() {
        //given
        UserUpdateForm form = UserTestFactory.createUpdateUserForm();
        User userTest = new User();

        userTest.setLogin("userTest");
        userTest.setEmail("userTest@wp.pl");

        form.setLogin("marian");
        form.setEmail("marian@wp.pl");
        //when
        when(userRepository.findByUuid(any())).thenReturn(Optional.of(userTest));
        systemUnderTest.editUser(form);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(userTest.getLogin()).isEqualTo(capturedUser.getLogin());
        assertThat(userTest.getEmail()).isEqualTo(capturedUser.getEmail());
    }

    @Test
    void checkIfThrowsWhenUsersIdNotFoundWhileEditing() {
        //given
        UserUpdateForm form = UserTestFactory.createUpdateUserForm();
        //then,when
        assertThatThrownBy(() -> systemUnderTest.editUser(form))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with id " + form.getUserUuid() + " not found");
    }

    @Test
    void checkIfDeletesUser() {
        //given
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        //when
        when(userRepository.existsByUuid(userUUID)).thenReturn(true);
        systemUnderTest.deleteUser(userUUID);
        //then
        verify(userRepository).removeUserByUuid(userUUID);
    }

    @Test
    void checkIfThrowsWhenUsersIdNotFoundWhileDeleting() {
        //given
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        //when
        when(userRepository.existsByUuid(userUUID)).thenReturn(false);
        assertThatThrownBy(() -> systemUnderTest.deleteUser(userUUID))
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
        assertThatThrownBy(() -> systemUnderTest.filterUsers(adminUUID, criteria, page))
                .isInstanceOf(NoContentException.class)
                .hasMessageContaining("No criteria included");
    }
}