package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Image;
import java.util.List;

public interface ImageService {

    List<Image> get();
    List<Image> getAll(List<Long> id);

    String delete(Long id);

}
