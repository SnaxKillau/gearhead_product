package ite.product.gearheadproduct.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String email;
    private String name;
    private String invoiceId;
    private Integer total;
    private LocalDate created;
    private String phoneNumber;
    private String address;
    @OneToMany(mappedBy = "invoice" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    @JsonManagedReference
    private List<TransformationSale> transformationSale;
}
