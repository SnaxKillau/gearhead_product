package ite.product.gearheadproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class DefaultTransformationDTO {
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
private String condition;
private String transmission;
private String brand;
private String color;
private List<String> image;
}
