package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
public class ServiceOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "services_with_orders_auto_serial")
    private Long servicesWithOrdersAutoSerial;

    @Column(name = "order_type")
    private Integer orderType; // Consider using an enum for order type

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "added_by")
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "com_code")
    private Integer comCode;

    @Column(name = "date")
    private LocalDate date;
}

