package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.service.ImageService;
import ite.product.gearheadproduct.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    public ImageRepository imageRepository;

    @Autowired
    public ImageService imageService;


    @PostMapping(path = {"/transformation"})
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        LocalTime time = LocalTime.now();
        Image image = imageRepository.save(Image.builder()
                .name(time.toString())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(image.getId());
    }
    @GetMapping(path = {"/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtil.decompressImage(dbImage.get().getImage()));
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<String> deleteImage(@PathVariable("id") Long id){
        String message = imageService.delete(id);
        return ResponseEntity.ok(message);
    }



}
