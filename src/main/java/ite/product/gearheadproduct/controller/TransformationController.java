package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.dto.TransformationDTO;
import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.entity.TransformationImportHistory;
import ite.product.gearheadproduct.mapper.TransformationImportHistoryMapper;
import ite.product.gearheadproduct.mapper.TransformationMapper;
import ite.product.gearheadproduct.repository.TransformationImportHistoryRepository;
import ite.product.gearheadproduct.service.TransformationService;
import ite.product.gearheadproduct.specification.TransformationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/transformation")
public class TransformationController {

    @Autowired
    private TransformationMapper transformationMapper;

    @Autowired
    private TransformationImportHistoryRepository importHistoryRepository;

    @Autowired
    private TransformationImportHistoryMapper transformationImportHistoryMapper;

    @Autowired
    private TransformationService transformationService;

    @GetMapping
    public ResponseEntity<List<Transformation>> get(){
        List<Transformation> transformations = transformationService.get();
        return ResponseEntity.ok(transformations);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody TransformationFilter transformationFilter){
        List<Transformation> transformations = transformationService.search(transformationFilter);
        return ResponseEntity.ok(transformations);
    }
    @PostMapping
    public ResponseEntity<Transformation> post(@RequestBody TransformationDTO dto){
        Transformation transformation = transformationService.post(transformationMapper.toTransformation(dto));
        return ResponseEntity.ok(transformation);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Transformation> update(@PathVariable Long id, @RequestBody TransformationDTO dto){
        Transformation transformation = transformationService.update(id, transformationMapper.toTransformation(dto));
        return ResponseEntity.ok(transformation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = transformationService.delete(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/import")
    public ResponseEntity<TransformationImportHistory> importTransformation
            (@RequestBody TransformationImportHistoryDTO dto){
        TransformationImportHistoryDTO transformationImportHistory = transformationService.importProduct(dto);
        TransformationImportHistory saveTransformationImportHistory = importHistoryRepository.save(transformationImportHistoryMapper.toTransformationImportHistory(transformationImportHistory));
        return ResponseEntity.ok(saveTransformationImportHistory);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<TransformationDetailDTO>> gotFilter(){
        List<TransformationDetailDTO> transformationDetailDTOS = transformationService.getFilter();
        return ResponseEntity.ok(transformationDetailDTOS);
    }

    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable Long id , @RequestBody String pathImage){
        boolean removed = transformationService.editImage(pathImage, id);
        return ResponseEntity.ok(removed);
    }

}

