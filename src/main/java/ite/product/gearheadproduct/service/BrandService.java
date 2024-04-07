package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Brand;
import ite.product.gearheadproduct.specification.BrandFilter;


import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> getBrand();
    public Brand postBrand(Brand brand);
    public Brand updateBrand(Long id, Brand brand);

    public Brand updateBrandName(Long id , String description);
    public String deleteBrand(Long id);
    public Brand getBrandById(Long id);

    public Map<Character , List<Brand>> groupBrand();
//    public Brand editImage(Long id, String imagePath);
    public List<Brand> searchBrand(BrandFilter brandFilter);

}
