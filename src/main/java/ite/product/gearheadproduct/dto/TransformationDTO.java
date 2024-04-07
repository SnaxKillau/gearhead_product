package ite.product.gearheadproduct.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransformationDTO {
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
    private Long condition_id;
    private Long transmission_id;
    private Long brand_id;
    private Long color_id;
    private List<Long> image_id;

}
