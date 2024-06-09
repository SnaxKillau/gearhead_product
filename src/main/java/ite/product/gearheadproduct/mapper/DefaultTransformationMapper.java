package ite.product.gearheadproduct.mapper;

import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import ite.product.gearheadproduct.entity.Transformation;

import java.util.stream.Collectors;


public class DefaultTransformationMapper {

    public DefaultTransformationDTO transformationMappers(Transformation transformation){
        DefaultTransformationDTO defaultTransformationDTO = DefaultTransformationDTO.builder()
                .id(transformation.getId())
                .model(transformation.getModel())
                .year(transformation.getYear())
                .availableUnit(transformation.getAvailableUnit())
                .price(transformation.getPrice())
                .descriptions(transformation.getDescriptions())
                .mpg(transformation.getMpg())
                .co2(transformation.getCo2())
                .power(transformation.getPower())
                .acceleration(transformation.getAcceleration())
                .top_speed(transformation.getTop_speed())
                .condition(transformation.getCondition().getDescriptions())
                .transmission(transformation.getTransmission().getDescriptions())
                .brand(transformation.getBrand().getDescription())
                .color(transformation.getColor().getDescription())
                .image(transformation.getImage().stream().map(e -> e.getName()).collect(Collectors.toList()))
                .build();
        return defaultTransformationDTO;
    }
}
