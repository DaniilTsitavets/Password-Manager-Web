package com.dt.manager.repository;

import com.dt.manager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Integer> {
    Optional<Password> findByServiceName(String serviceName);

}
