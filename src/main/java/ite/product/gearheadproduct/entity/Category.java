package ite.product.gearheadproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "category_transformation", // Name of the join table
            joinColumns = @JoinColumn(name = "category_id"), // Column in this entity's table
            inverseJoinColumns = @JoinColumn(name = "transformation_id") // Column in the other entity's table
    )
    private List<Transformation> transformation;
}
