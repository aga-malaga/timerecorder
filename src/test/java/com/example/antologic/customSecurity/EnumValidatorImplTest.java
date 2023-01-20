package com.example.antologic.customSecurity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EnumValidatorImplTest {

    private EnumValidatorImpl underTest = new EnumValidatorImpl();

    @Test
    void checkIfReturnsTrueWhenValueIsOnTheList() {
        //given
        String admin = "ADMIN";
        String employee = "EMPLOYEE";
        String manager = "MANAGER";
        //when
        //then
        Assertions.assertAll(
                () -> Assertions.assertTrue(underTest.validateEnum(admin)),
                () -> Assertions.assertTrue(underTest.validateEnum(employee)),
                () -> Assertions.assertTrue(underTest.validateEnum(manager)
                ));
    }

    @Test
    void checkIfThrowsExWhenValueIsNotOnTheList() {
        //given
        String role = "Role";
        //when,then
        assertThatThrownBy(() -> underTest.validateEnum(role))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(role + " this role does not exist");
    }
}