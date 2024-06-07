package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "treasuries_delivery")
public class TreasuryDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming auto-increment for id
    private Integer id;

    @Column(name = "treasuries_id", nullable = false)
    private Integer treasuryId;  // Likely a foreign key referencing Treasury

    @Column(name = "treasuries_can_delivery_id", nullable = false)
    private Integer treasuryCanDeliveryId;  // Likely a foreign key referencing Treasury

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "added_by", nullable = false)
    private Integer addedBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Column(name = "com_code", nullable = false)
//    private Integer comCode;

    // Getters and setters (optional but recommended)
    // ...
}

