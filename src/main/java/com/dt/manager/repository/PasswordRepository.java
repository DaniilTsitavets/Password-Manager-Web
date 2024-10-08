package com.dt.manager.repository;

import com.dt.manager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Integer> {
}
