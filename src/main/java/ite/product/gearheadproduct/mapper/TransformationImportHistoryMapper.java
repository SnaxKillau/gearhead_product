package ite.product.gearheadproduct.mapper;

import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.TransformationImportHistory;
import ite.product.gearheadproduct.service.TransformationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {TransformationService.class})
public interface TransformationImportHistoryMapper {

    @Mapping(target = "transformation" , source = "transformation_id" )
    public TransformationImportHistory toTransformationImportHistory (TransformationImportHistoryDTO dto);
}
