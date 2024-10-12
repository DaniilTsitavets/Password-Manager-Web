package com.dt.manager.service;

import com.dt.manager.entity.Password;
import com.dt.manager.repository.PasswordRepository;
import lombok.AllArgsConstructor;
import org.passay.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PasswordService {
    private PasswordRepository passwordRepository;
    private PasswordValidator passwordValidator;
    private PasswordGenerator passwordGenerator;

    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }

    public Optional<Password> getPasswordByServiceName(String serviceName) {
        return passwordRepository.findByServiceName(serviceName);
    }

    public Password createPassword(String passwordValue, String serviceName) {
        RuleResult result = passwordValidator.validate(new PasswordData(passwordValue));
        if (!result.isValid()) {
            throw new IllegalArgumentException("Invalid password: " + String.join(", ", passwordValidator.getMessages(result)));
        }
        Password password = new Password();
        password.setPasswordValue(passwordValue);
        password.setServiceName(serviceName);
        password.setCreatedAt(LocalDateTime.now());
        return passwordRepository.save(password);
    }

    public Password createPasswordWithGeneratedPassword(String serviceName) {
        Password password = new Password();
        password.setPasswordValue(generatePassword());
        password.setServiceName(serviceName);
        password.setCreatedAt(LocalDateTime.now());
        return passwordRepository.save(password);
    }

    public Optional<Password> updatePassword(String newPasswordValue, String serviceName) {
        Optional<Password> optionalPassword = passwordRepository.findByServiceName(serviceName);
        RuleResult result = passwordValidator.validate(new PasswordData(newPasswordValue));
        if (!result.isValid()) {
            throw new IllegalArgumentException("Invalid password: " + String.join(", ", passwordValidator.getMessages(result)));
        }
        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();
            password.setPasswordValue(newPasswordValue);
            password.setCreatedAt(LocalDateTime.now());
            return Optional.of(passwordRepository.save(password));
        }
        return Optional.empty();
    }

    public Optional<Password> updatePasswordWithGeneratedPassword(String serviceName) {
        Optional<Password> optionalPassword = passwordRepository.findByServiceName(serviceName);
        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();
            password.setPasswordValue(generatePassword());
            password.setCreatedAt(LocalDateTime.now());
            return Optional.of(passwordRepository.save(password));
        }
        return Optional.empty();
    }

    public String generatePassword() {
        return passwordGenerator.generatePassword(12, passwordValidator.getRules());
    }
}
