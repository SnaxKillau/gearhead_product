package ite.product.gearheadproduct.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.repository.TransformationImportHistoryRepository;
import ite.product.gearheadproduct.repository.TransformationRepository;
import ite.product.gearheadproduct.service.TransformationService;
import ite.product.gearheadproduct.specification.TransformationFilter;
import ite.product.gearheadproduct.specification.TransformationSpec;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransformationServiceImpl implements TransformationService {

    private final TransformationRepository transformationRepository;
    private final TransformationImportHistoryRepository transformationImportHistoryRepository;
    private final ImageRepository imageRepository;
    private ModelMapper modelMapper;
    @Override
    public List<Transformation> get(){
        List<Transformation> transformation = transformationRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Transformation::getId))
                .collect(Collectors.toList());
        return transformation;
    }

    @Override
    public Transformation post(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    @Override
    public Transformation update(Long id, Transformation transformation) {
        Transformation updateTransformation = transformationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Transformation"));
        modelMapper.map(transformation, updateTransformation);
        return transformationRepository.save(updateTransformation);
    }

    @Override
    public String delete(Long id) {
        Transformation deleteTransformation = transformationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Transformation"));
        deleteTransformation.getImage().forEach(image -> {
            imageRepository.delete(image);
        });
        transformationRepository.delete(deleteTransformation);
        String message = "Delete Successfully";
        return message;
    }

    @Override
    public Transformation findById(Long id) {
        Transformation findTransformation = transformationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Transformation"));
        return findTransformation;
    }

    @Override
    public TransformationImportHistoryDTO importProduct(TransformationImportHistoryDTO dto) {
        Transformation transformation = transformationRepository
                .findById(dto.getTransformation_id())
                .orElseThrow(() -> new ResourceNotFound(dto.getTransformation_id(),"Transformation"));
        transformation.setAvailableUnit(transformation.getAvailableUnit() + dto.getImportUnit());
        transformation.setPrice(dto.getPricePerUnit());
        return dto;
    }

    @Override
    public List<TransformationDetailDTO> getFilter() {
        return transformationRepository.getTransformationsWithDetails();
    }

    @Override
    public List<Transformation> search(TransformationFilter transformationFilter) {
        TransformationSpec transformationSpec = new TransformationSpec(transformationFilter);
        List<Transformation> transformations = transformationRepository.findAll(transformationSpec);
        return transformations;
    }

    @Override
    public boolean editImage(String path, Long id) {
        Optional<Transformation> transformationOptional = transformationRepository.findById(id);
        System.out.println("tran" + transformationOptional.isPresent());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(path);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String value = jsonNode.get("pathImage").asText();
        if(transformationOptional.isPresent()){
            Transformation transformation = transformationOptional.get();
            boolean removed = transformation.getImage().removeIf(image -> image.getName().equals(value));
           System.out.println("remove" + removed);
            if(removed){
                transformationRepository.save(transformation);
                return true;
            }
        }
        return false;
    }


}
