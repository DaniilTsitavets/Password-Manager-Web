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
@RequestMapping("/password")
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping
    public List<Password> getAllPasswords() {
        return passwordService.getAllPasswords();
    }

    @GetMapping("/{serviceName}")
    public ResponseEntity<Password> getPasswordByServiceName(@PathVariable String serviceName) {
        Optional<Password> password = passwordService.getPasswordByServiceName(serviceName);
        return password.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Password addPassword(@RequestBody Password password) {
        return passwordService.createPassword(password.getPasswordValue(), password.getServiceName());
    }

    @PostMapping("/generate")
    public ResponseEntity<Password> generatePasswordForService(@RequestBody String serviceName) {

        Password generatedPassword = passwordService
                .createPasswordWithGeneratedPassword(serviceName);

        return ResponseEntity.ok(generatedPassword);
    }

    @PutMapping("/{serviceName}/update")
    public ResponseEntity<Password> updatePassword(@PathVariable String serviceName,
                                                   @RequestBody Password password) {
        Optional<Password> updatedPassword = passwordService
                .updatePassword(password.getPasswordValue(), serviceName);

        return updatedPassword.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{serviceName}/update/generate")
    public ResponseEntity<Password> updatePasswordWithGeneratedPassword(@PathVariable String serviceName) {

        Optional<Password> updatedPassword = passwordService
                .updatePasswordWithGeneratedPassword(serviceName);
        return updatedPassword.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
