package ite.product.gearheadproduct.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String user_id;
    private String source;
    private String address;
    private String imagePath;
    private String userName;
    private String userEmail;
    private Boolean accept;
    private Boolean deny;
    private Boolean customerAccept;
    private Boolean customerDeny;
    private Integer price;
    private LocalDate created;
}
