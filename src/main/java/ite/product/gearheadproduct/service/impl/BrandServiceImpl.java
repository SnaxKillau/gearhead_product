package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Brand;
import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.BrandRepository;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.service.BrandService;
import ite.product.gearheadproduct.specification.BrandFilter;
import ite.product.gearheadproduct.specification.BrandSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;
    @Override
    public List<Brand> getBrand() {
        return brandRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Brand::getId))
                .collect(Collectors.toList());
    }
    @Override
    public Brand postBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Brand findBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id, "Brand"));
        findBrand.setDescription(brand.getDescription());
        findBrand.setImagePath(brand.getImagePath());
        findBrand.setCreated(brand.getCreated());
        return brandRepository.save(findBrand);
    }

    @Override
    public Brand updateBrandName(Long id, String description) {
        Brand findBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id, "Brand"));
        findBrand.setDescription(description);
        findBrand.setImagePath(findBrand.getImagePath());
        findBrand.setCreated(findBrand.getCreated());
        return  brandRepository.save(findBrand);
    }

    @Override
    public String deleteBrand(Long id) {
        Brand findBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id, "Brand"));
       Optional<Image> imageOptional = imageRepository.findByName(findBrand.getImagePath());
        if(imageOptional.isPresent()){
            imageRepository.delete(imageOptional.get());
        }
        brandRepository.delete(findBrand);
        String message = "Delete Brand Successfully";
        return message;
    }
    @Override
    public Brand getBrandById(Long id) {
        Brand findBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id, "Brand"));
        return findBrand;
    }

    @Override
    public Map<Character, List<Brand>> groupBrand() {
        List<Brand> brands = brandRepository.findAll();
        Map<Character , List<Brand>> brandMap = brands.stream()
                .collect(Collectors.groupingBy(data -> data.getDescription().charAt(0)));
        return brandMap;
    }

    @Override
    public List<Brand> searchBrand(BrandFilter brandFilter) {
        BrandSpec brandSpec = new BrandSpec(brandFilter);
        List<Brand> brands = brandRepository.findAll(brandSpec);
        return brands;
    }

//    @Override
//    public Brand editImage(Long id, String imagePath) {
//        Brand findBrand = brandRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFound(id, "Brand"));
//        findBrand.setImagePath(imagePath);
//        findBrand.setDescription(findBrand.getDescription());
//        findBrand.setCreated(findBrand.getCreated());
//        return brandRepository.save(findBrand);
//    }


}
