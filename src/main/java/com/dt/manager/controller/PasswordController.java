package com.dt.manager.controller;

import com.dt.manager.entity.Password;
import com.dt.manager.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping
    public List<Password> getAllPasswords() {
        return passwordService.getAllPasswords();
    }

    @GetMapping("/{id}")
    public Optional<Password> getPasswordById(@PathVariable Integer id) {
        return passwordService.getPasswordById(id);
    }

    @PostMapping
    public Password addPassword(@RequestBody Password password) {
        return passwordService.createPassword(password.getPasswordValue(), password.getServiceName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Password> updatePassword(@PathVariable int id, @RequestBody Password password) {
        Optional<Password> updatedPassword = passwordService.updatePassword(id, password.getPasswordValue(), password.getServiceName());
        return updatedPassword.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
