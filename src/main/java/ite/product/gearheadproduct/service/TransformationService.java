package ite.product.gearheadproduct.service;


import ite.product.gearheadproduct.dto.DefaultTransformationDTO;
import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.response.TopTransformationResponse;
import ite.product.gearheadproduct.response.TransformationWithPageResponse;
import ite.product.gearheadproduct.specification.TransformationFilter;

import java.util.List;

public interface TransformationService {
    public List<DefaultTransformationDTO> get(int pageNumber , int pageLimit);

    public TransformationWithPageResponse getByBrand(String brandName , int pageNumber , int pageLimit);

    public TopTransformationResponse getTop();

    public DefaultTransformationDTO getById(Long id);
    public Transformation post(Transformation transformation);
    public Transformation update(Long id, Transformation transformation);

    public String delete(Long id);
    public Transformation findById(Long id);

    public TransformationImportHistoryDTO importProduct(TransformationImportHistoryDTO dto);

    public List<TransformationDetailDTO> getFilter();

    public List<DefaultTransformationDTO> search(TransformationFilter transformationFilter);

    public boolean editImage(String path , Long id);



}
