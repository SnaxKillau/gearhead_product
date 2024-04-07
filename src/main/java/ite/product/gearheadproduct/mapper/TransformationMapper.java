package ite.product.gearheadproduct.mapper;

import ite.product.gearheadproduct.dto.TransformationDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.service.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring" , uses = {ImageService.class , ConditionService.class, TransmissionService.class, ColorService.class, BrandService.class})
public interface TransformationMapper {
    TransformationMapper INSTANCE = Mappers.getMapper(TransformationMapper.class);

    @Mapping(target = "condition" , source = "condition_id")
    @Mapping(target = "transmission", source = "transmission_id")
    @Mapping(target = "image", source = "image_id")
    @Mapping(target = "brand", source = "brand_id")
    @Mapping(target = "color", source = "color_id")
    Transformation toTransformation(TransformationDTO dto);
}
