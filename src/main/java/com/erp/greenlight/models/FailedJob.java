package com.erp.greenlight.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "failed_jobs")
public class FailedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false, columnDefinition = "text")
    private String connection;

    @Column(nullable = false, columnDefinition = "text")
    private String queue;

    @Column(nullable = false, columnDefinition = "longtext")
    private String payload;

    @Column(nullable = false, columnDefinition = "longtext")
    private String exception;

    @Column(nullable = false)
    private LocalDateTime failedAt = LocalDateTime.now();

}

