package ite.product.gearheadproduct.service;


import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.dto.TransformationImportHistoryDTO;
import ite.product.gearheadproduct.entity.Transformation;
import ite.product.gearheadproduct.specification.TransformationFilter;

import java.util.List;

public interface TransformationService {
    public List<Transformation> get();
    public Transformation post(Transformation transformation);
    public Transformation update(Long id, Transformation transformation);

    public String delete(Long id);
    public Transformation findById(Long id);

    public TransformationImportHistoryDTO importProduct(TransformationImportHistoryDTO dto);

    public List<TransformationDetailDTO> getFilter();

    public List<Transformation> search(TransformationFilter transformationFilter);

    public boolean editImage(String path , Long id);



}
