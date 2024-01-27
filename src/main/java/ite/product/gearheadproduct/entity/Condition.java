package ite.product.gearheadproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descriptions;
}
