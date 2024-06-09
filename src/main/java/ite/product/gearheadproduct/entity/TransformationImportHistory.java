package ite.product.gearheadproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "transformationImportHistories")
public class TransformationImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateImport;
    private Integer importUnit;
    private Integer pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "transformations_id")
    private Transformation transformation;
//    private LocalDate createDate;
}
