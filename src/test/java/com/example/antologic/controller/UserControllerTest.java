package com.example.antologic.controller;

import com.example.antologic.service.UserService;
import com.example.antologic.user.Role;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private Validator validator;


    @DisplayName("the checked form contains wrong email format")
    @Test
    void checkIfFormValidatesContent() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory())
        {
            validator = factory.getValidator();
            UserCreateForm form = new UserCreateForm("login", "name", "surname", Role.EMPLOYEE, "email", "password", BigDecimal.ONE);
            Set<ConstraintViolation<UserCreateForm>> violations = validator.validate(form);

            assertNotEquals(0, violations.size());
        }
    }

    @Test
    void checkIfReturnsListOfUsersDto() throws Exception {
        //given
        UUID adminUuid = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .param("adminUuid", String.valueOf(adminUuid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void checkIfCreatesAccountAfterValidation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UUID adminUuid = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");

        UserCreateForm form = new UserCreateForm(
                "login2",
                "name",
                "surname",
                Role.EMPLOYEE,
                "emai222l@wp.pl",
                "password",
                BigDecimal.ONE
        );
        UserDTO dto = new UserDTO(UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0"), form.getLogin(),
                form.getName(), form.getSurname(), form.getRole(), form.getEmail(), form.getCostPerHour());


        when(userService.createUser(adminUuid, form)).thenReturn(dto);

        final String jsonString = objectMapper.writeValueAsString(form);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .param("adminUuid", String.valueOf(adminUuid))
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("testInvalidData")
    void checkIfThrowsExceptionWhenIncorrectForm(String login,
                                                 String name,
                                                 String surname,
                                                 Role role,
                                                 String email,
                                                 String password,
                                                 BigDecimal cost) throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        UserCreateForm form = new UserCreateForm(
                login, name, surname, role, email, password, cost);

        UUID adminUuid = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
        final String jsonString = objectMapper.writeValueAsString(form);

        //when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .param("adminUuid", String.valueOf(adminUuid))
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Stream<Arguments> testInvalidData() {
        return Stream.of(
                arguments("", "testName", "testLastname", Role.ADMIN,"password", "email@email.com" , BigDecimal.ONE),
                arguments("testLogin", "", "testLastname", Role.ADMIN,"password", "email@email.com" , BigDecimal.ONE),
                arguments("testLogin", "testName", "", Role.ADMIN,"password", "email@email.com" , BigDecimal.ONE),
                arguments("testLogin", "testName", "testLastname", null,"password", "email@email.com" , BigDecimal.ONE),
                arguments("testLogin", "testName", "testLastname", Role.ADMIN,"", "email@email.com" , BigDecimal.ONE),
                arguments("testLogin", "testName", "testLastname", Role.ADMIN,"password", "" , BigDecimal.ONE),
                arguments("testLogin", "testName", "testLastname", Role.ADMIN,"password", "email@email.com" , null),
                arguments("", "testName", "", Role.ADMIN,"password", "email@email.com" , BigDecimal.ONE),
                arguments("", "testName", "", Role.ADMIN,"password", "" , BigDecimal.ONE)
        );

    }
}
