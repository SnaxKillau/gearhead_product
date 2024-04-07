package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.service.ImageService;
import ite.product.gearheadproduct.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public List<Image> get() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> getAll(List<Long> id) {
        return imageRepository.findAllById(id);
    }

    @Override
    public String delete(Long id) {
//        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id , "Image"));
//        imageRepository.delete(image);
        return "Delete Successfully";
    }


}
