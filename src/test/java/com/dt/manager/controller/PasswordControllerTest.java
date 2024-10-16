package com.dt.manager.controller;

import com.dt.manager.entity.Password;
import com.dt.manager.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PasswordController.class)
public class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordService passwordService;

    private Password password;

    @BeforeEach
    void setUp() {
        password = new Password();
        password.setServiceName("example");
        password.setPasswordValue("generatedPassword");
    }

    @Test
    void testGetPasswordByServiceNameFound() throws Exception {
        // Когда пароль найден
        when(passwordService.getPasswordByServiceName(anyString()))
                .thenReturn(Optional.of(password));

        mockMvc.perform(get("/password/example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceName").value("example"))
                .andExpect(jsonPath("$.passwordValue").value("generatedPassword"));
    }

    @Test
    void testGetPasswordByServiceNameNotFound() throws Exception {
        when(passwordService.getPasswordByServiceName(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/password/not-correct"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGeneratePasswordForService() throws Exception {
        when(passwordService.generatePassword()).thenReturn("generatedPassword");
        when(passwordService.createPassword(anyString(), anyString())).thenReturn(password);

        mockMvc.perform(post("/password")
                .param("serviceName", password.getServiceName())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.serviceName").value(password.getServiceName()))
            .andExpect(jsonPath("$.passwordValue").value("generatedPassword"));
    }

    @Test
    void testUpdatePasswordWithGeneratedPassword() throws Exception {
        when(passwordService.generatePassword()).thenReturn("newGeneratedPassword");
        when(passwordService.updatePassword(anyString(), anyString())).thenReturn(Optional.of(password));

        mockMvc.perform(put("/password/example")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.serviceName").value("example"))
            .andExpect(jsonPath("$.passwordValue").value("generatedPassword"));
    }

    @Test
    void testUpdatePasswordWithGeneratedPasswordNotFound() throws Exception {
        when(passwordService.updatePasswordWithGeneratedPassword(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/password/nonexistent/update/generate"))
                .andExpect(status().isNotFound());
    }
}
