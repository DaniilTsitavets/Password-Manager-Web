package com.dt.manager.controller;

import com.dt.manager.entity.Password;
import com.dt.manager.entity.PasswordDto;
import com.dt.manager.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/password")
@Validated
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
    public ResponseEntity<Password> addPassword(@RequestBody PasswordDto passwordDto) {
        Password password = passwordService
                .createPassword(passwordDto.getPasswordValue(), passwordDto.getServiceName());
        return ResponseEntity.ok(password);
    }

    @PostMapping("/generate")
    public ResponseEntity<Password> generatePasswordForService(@RequestBody PasswordDto passwordDto) {

        Password generatedPassword = passwordService
                .createPasswordWithGeneratedPassword(passwordDto.getServiceName());

        return ResponseEntity.ok(generatedPassword);
    }

    @PutMapping("/{serviceName}/update")
    public ResponseEntity<Password> updatePassword(@PathVariable String serviceName,
                                                   @RequestBody PasswordDto passwordDto) {

        Optional<Password> updatedPassword = passwordService
                .updatePassword(passwordDto.getPasswordValue(), serviceName);
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
