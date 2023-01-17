package com.example.antologic.controller;

import com.example.antologic.service.UserService;
import com.example.antologic.user.Role;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        UserForm form = new UserForm("login", "name", "surname", Role.EMPLOYEE, "email", "password", BigDecimal.ONE);
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);

        assertNotEquals(0, violations.size());
    }

    @Test
    void checkIfReturnsListOfUsersDto() throws Exception {
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

        UserForm form = new UserForm(
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

//    @Test
//    void checkIfThrowsExceptionWhenIncorrectForm() throws Exception {
//        //given
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserForm form = new UserForm(
//                "",
//                "name",
//                "surname",
//                Role.EMPLOYEE,
//                "email",
//                "password",
//                BigDecimal.ONE
//        );
//        UUID adminUuid = UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0");
//        final String jsonString = objectMapper.writeValueAsString(form);
//
//        //when, then
//        mockMvc
//                .perform(post("/api/user")
//                        .param("adminUuid", String.valueOf(adminUuid))
//                        .content(jsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }

}
