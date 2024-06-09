package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Brand;
import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.service.BrandService;
import ite.product.gearheadproduct.specification.BrandFilter;
import ite.product.gearheadproduct.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ImageRepository imageRepository;



    @GetMapping
    public ResponseEntity<List<Brand>> get(){
        List<Brand> brand = brandService.getBrand();
        return ResponseEntity.ok(brand);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id){
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            return ResponseEntity.ok(brandService.deleteBrand(id));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete brand due to associated relationships");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete brand");
        }

    }
    @PostMapping("/admin")
    public ResponseEntity<Brand> uploadImage(@RequestParam String description,
                                             @RequestParam LocalDate created,
                                             @RequestPart("file") MultipartFile file) throws IOException {
        Brand brand = new Brand();
        LocalTime time = LocalTime.now();
        imageRepository.save(Image.builder()
                .name(time.toString())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        brand.setImagePath(time.toString());
        brand.setDescription(description);
        brand.setCreated(created);
        Brand postingBrand = brandService.postBrand(brand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postingBrand);
    }
    @PatchMapping("/admin/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id ,
                                        @RequestParam String description,
                                        @RequestParam LocalDate created,
                                        @RequestPart("file") MultipartFile file)throws IOException {
        if(!file.isEmpty()){
            LocalTime time = LocalTime.now();
            imageRepository.save(Image.builder()
                    .name(time.toString())
                    .type(file.getContentType())
                    .image(ImageUtil.compressImage(file.getBytes())).build());
            Brand brand = new Brand();
            brand.setImagePath(time.toString());
            brand.setDescription(description);
            brand.setCreated(created);
            Brand updateBrand = brandService.updateBrand(id , brand);
            return ResponseEntity.ok(updateBrand);
        }
        else {
            Brand brand = brandService.updateBrandName(id , description);
            return ResponseEntity.ok(brand);
        }

    }

    @GetMapping("/group")
    public ResponseEntity<Map<Character , List<Brand>>> getGroupBrand(){
        Map<Character , List<Brand>> brandMap = brandService.groupBrand();
        return ResponseEntity.ok(brandMap);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Brand>> search(@RequestBody BrandFilter brandFilter){
        if(!brandFilter.getDescription().isEmpty()){
            List<Brand> brands = brandService.searchBrand(brandFilter);
            return ResponseEntity.ok(brands);
        }
        else {
            List<Brand> brands = new ArrayList<>();
            return ResponseEntity.ok(brands);

        }

    }

//    @PostMapping("/editImage/{id}")
//    public ResponseEntity<Brand> editImage(@PathVariable Long id,
//                                           @RequestPart("file") MultipartFile file)throws IOException{
//        LocalTime time = LocalTime.now();
//        imageRepository.save(Image.builder()
//                .name(time.toString())
//                .type(file.getContentType())
//                .image(ImageUtil.compressImage(file.getBytes())).build());
//        Brand brand = brandService.editImage(id, time.toString());
//        return ResponseEntity.ok(brand);
//    }
}
