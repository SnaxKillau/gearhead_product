package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import ite.product.gearheadproduct.dto.TransformationDTO;
import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.entity.TransformationImportHistory;
import ite.product.gearheadproduct.mapper.TransformationImportHistoryMapper;
import ite.product.gearheadproduct.mapper.TransformationMapper;
import ite.product.gearheadproduct.repository.TransformationImportHistoryRepository;
import ite.product.gearheadproduct.response.TopTransformationResponse;
import ite.product.gearheadproduct.response.TransformationWithPageResponse;
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
    public ResponseEntity<List<DefaultTransformationDTO>> get(@RequestParam("pageNum") int pageNum , @RequestParam("pageLimit") int pageLimit){
        List<DefaultTransformationDTO> transformations = transformationService.get(pageNum, pageLimit);
        return ResponseEntity.ok(transformations);
    }
    @GetMapping("/{name}")
    public ResponseEntity<TransformationWithPageResponse> getByBrandName(@PathVariable String name , @RequestParam("pageNum") int pageNum , @RequestParam("pageLimit") int pageLimit){
        TransformationWithPageResponse transformations = transformationService.getByBrand(name , pageNum, pageLimit);
        return ResponseEntity.ok(transformations);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DefaultTransformationDTO> getById(@PathVariable Long id){
        DefaultTransformationDTO defaultTransformationDTO = transformationService.getById(id);
        return ResponseEntity.ok(defaultTransformationDTO);
    }
    @GetMapping("/top")
    public ResponseEntity<TopTransformationResponse> getTop(){
        TopTransformationResponse topTransformationResponse = transformationService.getTop();
        return ResponseEntity.ok(topTransformationResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody TransformationFilter transformationFilter){
        List<DefaultTransformationDTO> transformations = transformationService.search(transformationFilter);
        return ResponseEntity.ok(transformations);
    }
    @PostMapping("/admin")
    public ResponseEntity<Transformation> post(@RequestBody TransformationDTO dto){
        Transformation transformation = transformationService.post(transformationMapper.toTransformation(dto));
        return ResponseEntity.ok(transformation);
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<Transformation> update(@PathVariable Long id, @RequestBody TransformationDTO dto){
        Transformation transformation = transformationService.update(id, transformationMapper.toTransformation(dto));
        return ResponseEntity.ok(transformation);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = transformationService.delete(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/admin/import")
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

