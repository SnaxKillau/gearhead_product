package ite.product.gearheadproduct.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private String senderUsername;
    private String senderEmail;
    private String receiverId;
    private String senderId;
    private Boolean confirmMessage;
    private Boolean accepted;
    private Boolean seen;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private LocalDate created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Notification parent;
    @OneToMany(mappedBy = "parent" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> children;
}
