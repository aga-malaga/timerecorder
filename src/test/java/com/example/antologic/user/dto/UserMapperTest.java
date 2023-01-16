package com.example.antologic.user.dto;

import com.example.antologic.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {

    @Test
    void checkIfMapsFormToUser() {
        //given
        UserForm form = new UserForm();
        form.setLogin("userform");
        //when
        final User user = UserMapper.toUser(form);
        //then
        assertThat(UserMapper.toUser(form).getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void checkIfMapsUserToDTO() {
        //given
        User user = new User();
        user.setLogin("userTest");
        //when
        final UserDTO userDTO = UserMapper.toDto(user);
        //then
        assertThat(UserMapper.toDto(user).login()).isEqualTo(user.getLogin());

    }
}