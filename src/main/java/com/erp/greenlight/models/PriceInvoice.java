package com.erp.greenlight.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "price_invoices")
@EntityListeners({AuditingEntityListener.class})
public class PriceInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "invoice_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate invoiceDate;


    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;


    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;


    @OneToMany(mappedBy = "priceInvoice")
    private List<PriceInvoiceDetail> priceInvoiceDetails;

    @CreatedBy
    @ManyToOne()
    @JoinColumn(name = "added_by",referencedColumnName = "id")
    private Admin addedBy;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @ManyToOne()
    @JoinColumn(name = "updated_by",referencedColumnName = "id")
    private Admin updatedBy;
    //newly added
    @Column(name = "customer")
    private String customer;
    @Column(name = "note")
    private String note;
    @Column(name = "tax_included")
    private boolean taxIncluded;
    @Column(name = "offer_duration")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate offerDuration;
    @Column(name = "delivery_location")
    private String deliveryLocation;

    public PriceInvoice(Long id) {
        this.id = id;
    }
}
