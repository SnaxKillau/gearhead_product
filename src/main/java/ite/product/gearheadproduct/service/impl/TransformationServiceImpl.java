package ite.product.gearheadproduct.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.mapper.DefaultTransformationMapper;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.repository.TransformationImportHistoryRepository;
import ite.product.gearheadproduct.repository.TransformationRepository;
import ite.product.gearheadproduct.response.TopTransformationResponse;
import ite.product.gearheadproduct.response.TransformationWithPageResponse;
import ite.product.gearheadproduct.service.TransformationService;
import ite.product.gearheadproduct.specification.TransformationFilter;
import ite.product.gearheadproduct.specification.TransformationSpec;
import ite.product.gearheadproduct.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private DefaultTransformationMapper mapper = new DefaultTransformationMapper();

    @Override
    public List<DefaultTransformationDTO> get(int pageNumber , int pageLimit){
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        List<Transformation> transformation = transformationRepository.findAll(pageable)
                .stream()
                .sorted(Comparator.comparingLong(Transformation::getId))
                .collect(Collectors.toList());
        List<DefaultTransformationDTO> defaultTransformationDTOS  = transformation.stream()
                .map(data -> mapper.transformationMappers(data))
                .collect(Collectors.toList());
        return defaultTransformationDTOS;
    }

    @Override
    public TransformationWithPageResponse getByBrand(String brandName, int pageNumber, int pageLimit) {
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        TransformationWithPageResponse transformationResponse = new TransformationWithPageResponse();
        Page<Transformation> transformationsPage = transformationRepository.findByBrandDescription(brandName, pageable);

        // Retrieve the list of transformations
        List<Transformation> transformations = transformationsPage.getContent()
                .stream()
                .sorted(Comparator.comparingLong(Transformation::getId))
                .collect(Collectors.toList());

        List<DefaultTransformationDTO> defaultTransformationDTOS  = transformations.stream()
                .map(data -> mapper.transformationMappers(data))
                .collect(Collectors.toList());


        //assign the total of page and defaultTransformationDTOS to response
        transformationResponse.setNumberOfPage(transformationsPage.getTotalPages());
        transformationResponse.setTransformationDTOS(defaultTransformationDTOS);

        return transformationResponse;
    }

    @Override
    public TopTransformationResponse getTop() {
        TopTransformationResponse topTransformationResponse = new TopTransformationResponse();
        List<Transformation> topTransformation = transformationRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Transformation::getId).reversed())
                .limit(3)
                .collect(Collectors.toList());
        List<DefaultTransformationDTO> topTransformationDTOS = topTransformation.stream()
                .map(data -> mapper.transformationMappers(data))
                .collect(Collectors.toList());
        List<Transformation> priceTransformation = transformationRepository.getTransformationPrice();
        List<DefaultTransformationDTO> priceTransformationDTOS =priceTransformation.stream()
                .map(data -> mapper.transformationMappers(data))
                .limit(3)
                .collect(Collectors.toList());
        topTransformationResponse.setTopTransformation(topTransformationDTOS);
        topTransformationResponse.setBudgetTransformation(priceTransformationDTOS);
        return topTransformationResponse;

    }

    @Override
    public DefaultTransformationDTO getById(Long id) {
        Transformation transformation = transformationRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id , "Transformation"));
        DefaultTransformationDTO defaultTransformationDTO = mapper.transformationMappers(transformation);
        return defaultTransformationDTO;
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

        System.out.println(transformation);
        //u[date property
        updateTransformation.setId(updateTransformation.getId());
        updateTransformation.setModel(transformation.getModel());
        updateTransformation.setYear(transformation.getYear());
        updateTransformation.setAvailableUnit(transformation.getAvailableUnit());
        updateTransformation.setPrice(transformation.getPrice());
        updateTransformation.setDescriptions(transformation.getDescriptions());
        updateTransformation.setMpg(transformation.getMpg());
        updateTransformation.setCo2(transformation.getCo2());
        updateTransformation.setPower(transformation.getPower());
        updateTransformation.setAcceleration(transformation.getAcceleration());
        updateTransformation.setTop_speed(transformation.getTop_speed());
        updateTransformation.setCondition(transformation.getCondition());
        updateTransformation.setTransmission(transformation.getTransmission());
        updateTransformation.setImage(transformation.getImage());
        updateTransformation.setBrand(transformation.getBrand());
        updateTransformation.setColor(transformation.getColor());

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
    public List<DefaultTransformationDTO> search(TransformationFilter transformationFilter) {
        TransformationSpec transformationSpec = new TransformationSpec(transformationFilter);

        List<Transformation> transformations = transformationRepository.findAll(transformationSpec);
        List<Transformation> transformationList = transformations
                .stream()
                .sorted(Comparator.comparingLong(Transformation::getId))
                .collect(Collectors.toList());
        List<DefaultTransformationDTO> defaultTransformationDTOS = transformationList
                .stream()
                .map(data -> mapper.transformationMappers(data))
                .collect(Collectors.toList());
        return defaultTransformationDTOS;
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
