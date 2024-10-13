package com.dt.manager.service;

import com.dt.manager.entity.Password;
import com.dt.manager.repository.PasswordRepository;
import com.dt.manager.validator.Generator;
import com.dt.manager.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.passay.PasswordData;
import org.passay.RuleResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PasswordServiceTest {

    @Mock
    private PasswordRepository passwordRepository;

    @Mock
    private Validator passwordValidator;

    @Mock
    private Generator passwordGenerator;

    @InjectMocks
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPassword_ShouldReturnSavedPassword_WhenValidPassword() {
        String passwordValue = "ValidPass1!";
        String serviceName = "TestService";

        Password password = new Password();
        password.setPasswordValue(passwordValue);
        password.setServiceName(serviceName);
        password.setCreatedAt(LocalDateTime.now());

        RuleResult ruleResult = new RuleResult();
        ruleResult.setValid(true);

        when(passwordValidator.validate(any(PasswordData.class))).thenReturn(ruleResult);
        when(passwordRepository.save(any(Password.class))).thenReturn(password);

        Password result = passwordService.createPassword(passwordValue, serviceName);

        assertNotNull(result);
        assertEquals(serviceName, result.getServiceName());
        verify(passwordRepository, times(1)).save(any(Password.class));
    }

    @Test
    void createPassword_ShouldThrowException_WhenInvalidPassword() {
        String passwordValue = "invalid";
        String serviceName = "TestService";

        RuleResult ruleResult = new RuleResult();
        ruleResult.setValid(false);

        when(passwordValidator.validate(any(PasswordData.class))).thenReturn(ruleResult);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordService.createPassword(passwordValue, serviceName);
        });

        assertTrue(exception.getMessage().contains("Invalid password"));
        verify(passwordRepository, never()).save(any(Password.class));
    }

    @Test
    void updatePassword_ShouldUpdatePassword_WhenPasswordExists() {
        String newPassword = "NewValidPass1!";
        String serviceName = "TestService";

        Password password = new Password();
        password.setServiceName(serviceName);
        password.setPasswordValue("OldPassword");

        when(passwordRepository.findByServiceName(serviceName)).thenReturn(Optional.of(password));

        RuleResult ruleResult = new RuleResult();
        ruleResult.setValid(true);
        when(passwordValidator.validate(any(PasswordData.class))).thenReturn(ruleResult);
        when(passwordRepository.save(any(Password.class))).thenReturn(password);

        Optional<Password> result = passwordService.updatePassword(newPassword, serviceName);

        assertTrue(result.isPresent());
        assertEquals(newPassword, result.get().getPasswordValue());
        verify(passwordRepository, times(1)).save(any(Password.class));
    }

    @Test
    void generatePassword_ShouldReturnGeneratedPassword() {
        String generatedPassword = "GeneratedPass1!";
        when(passwordGenerator.generatePassword(anyInt(), anyList())).thenReturn(generatedPassword);

        String result = passwordService.generatePassword();

        assertEquals(generatedPassword, result);
    }

    @Test
    void updatePasswordWithGeneratedPassword_ShouldUpdatePassword_WhenPasswordExists() {
        String serviceName = "TestService";
        String newGeneratedPassword = "GeneratedPass1!";

        Password password = new Password();
        password.setServiceName(serviceName);
        password.setPasswordValue("OldPassword");

        when(passwordRepository.findByServiceName(serviceName)).thenReturn(Optional.of(password));
        when(passwordGenerator.generatePassword(anyInt(), anyList())).thenReturn(newGeneratedPassword);
        when(passwordRepository.save(any(Password.class))).thenReturn(password);

        Optional<Password> result = passwordService.updatePasswordWithGeneratedPassword(serviceName);

        assertTrue(result.isPresent());
        assertEquals(newGeneratedPassword, result.get().getPasswordValue());
        verify(passwordRepository, times(1)).save(any(Password.class));
    }
}
