package ite.product.gearheadproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "transformations")
public class Transformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private Integer year;
    private Integer availableUnit;
    private Integer price;
    private String descriptions;
    private Integer mpg;
    private Integer co2;
    private Integer power;
    private Integer acceleration;
    private Integer top_speed;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @OneToMany
    @JoinColumn(name = "image_id")
    private List<Image> image;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
//    private LocalDate createDate;



}
