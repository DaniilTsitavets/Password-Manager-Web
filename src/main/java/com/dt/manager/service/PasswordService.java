package com.dt.manager.service;

import com.dt.manager.entity.Password;
import com.dt.manager.repository.PasswordRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class PasswordService {
    private PasswordRepository passwordRepository;

    public Password createPassword(String passwordValue, String serviceName) {
        Password password = new Password();
        password.setPasswordValue(passwordValue);
        password.setServiceName(serviceName);
        password.setCreatedAt(LocalDateTime.now());
        return passwordRepository.save(password);
    }

    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }

    public Optional<Password> getPasswordById(int id) {
        return passwordRepository.findById(id);
    }

    public Optional<Password> updatePassword(int id, String newPasswordValue, String newServiceName) {
        Optional<Password> optionalPassword = passwordRepository.findById(id);
        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();
            password.setPasswordValue(newPasswordValue);
            password.setServiceName(newServiceName);
            password.setCreatedAt(LocalDateTime.now());
            return Optional.of(passwordRepository.save(password));
        }
        return Optional.empty();
    }
}
