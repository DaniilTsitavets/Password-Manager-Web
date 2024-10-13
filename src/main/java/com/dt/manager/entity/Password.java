package com.dt.manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Table
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String passwordValue;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
