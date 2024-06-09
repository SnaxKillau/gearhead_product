package ite.product.gearheadproduct.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    private String name;
    private String user_id;
    private String source;
    private String address;
    private String imagePath;
    private LocalDate created;
}
