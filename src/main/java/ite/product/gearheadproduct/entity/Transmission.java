package ite.product.gearheadproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transmissions")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String descriptions;
}
