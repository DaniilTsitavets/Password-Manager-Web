package com.dt.manager.controller;

import com.dt.manager.entity.Password;
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
  public ResponseEntity<Password> addPassword(
      @RequestParam String serviceName,
      @RequestParam(required = false) String passwordValue) {

    Password password = passwordService.createPassword(
        passwordValue != null ? passwordValue : passwordService.generatePassword(), serviceName);

    return ResponseEntity.ok(password);
  }

  @PutMapping("/{serviceName}")
  public ResponseEntity<Password> updatePassword(
      @PathVariable String serviceName,
      @RequestParam(required = false) String passwordValue) {

    Optional<Password> updatedPassword = passwordService.updatePassword(
        passwordValue != null ? passwordValue : passwordService.generatePassword(),
        serviceName);

    return updatedPassword.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
