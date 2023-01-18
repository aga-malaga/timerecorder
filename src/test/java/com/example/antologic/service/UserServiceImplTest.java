package com.example.antologic.service;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.NoContentException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.customSecurity.AdminValidator;
import com.example.antologic.filter.SearchCriteria;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
    void checkIfGetsAllUsers() {
        //given
        User user = new User();
        user.setEmail("email");
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        // when
        List<UserDTO> expected = underTest.findUsers(UUID.randomUUID());
        // then
        assertThat(expected.get(0).email()).isEqualTo(users.get(0).getEmail());
        verify(userRepository).findAll();
    }

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
    void checkIfFindsUsersPaged() {
        //when
        underTest.findUsersPaged(UUID.randomUUID(), 0,3,"name");
        //then
        verify(userRepository).findAll(PageRequest.of(0, 3));

    }

    @Test
    void checkIfCreatesUser() {
        // given
        UserForm userForm = new UserForm();
        userForm.setLogin("login");
        User user = new User();
        user.setLogin("login");

        // when
        underTest.createUser(UUID.randomUUID(), userForm);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        final User userArgumentCaptorValue = userArgumentCaptor.getValue();

        assertThat(userArgumentCaptorValue.getLogin()).isEqualTo(user.getLogin());

    }

    @Test
    void checkIfWillThrowWhenLoginExistsWhileCreatingUser() {
        // given
        UserForm userForm = new UserForm();

        given(userRepository.existsByLogin(userForm.getLogin()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.createUser(UUID.randomUUID(), userForm))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("This login already exists");
    }

    @Test
    void checkIfEditsUser() {
        //given
        UUID adminUUID = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        UUID userUUID = UUID.fromString("9f05e7e3-6382-482e-a40d-78074569aed2");
        UserForm form = new UserForm();
        User userTest = new User();

        userTest.setLogin("userTest");
        userTest.setEmail("userTest@wp.pl");

        form.setLogin("marian");
        form.setEmail("marian@wp.pl");
        //when
        when(userRepository.findByUuid(userUUID)).thenReturn(Optional.of(userTest));
        underTest.editUser(adminUUID, userUUID, form);
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
        UUID adminUUID = UUID.randomUUID();
        UUID userUUID = UUID.fromString("697fa342-cc86-4757-ab6c-cc360d3ea2a0");
        UserForm form = new UserForm();
        User user = new User();
        //when
        when(userRepository.findByUuid(userUUID)).thenReturn(Optional.of(user));
        underTest.editUser(adminUUID, userUUID, form);
        //then
        verify(userRepository).findByUuid(userUUID);
        verify(adminValidator).validate(adminUUID);
    }

    @Test
    void checkIfThrowsWhenUsersIdNotFoundWhileEditing() {
        //given
        UUID adminUUID = UUID.randomUUID();
        UUID userUUID = UUID.fromString("697fa342-cc86-4757-ab6c-cc360d3ea2a0");
        UserForm form = new UserForm();
        //then,when
        assertThatThrownBy(() -> underTest.editUser(adminUUID, userUUID, form))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with id " + userUUID + " not found");
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