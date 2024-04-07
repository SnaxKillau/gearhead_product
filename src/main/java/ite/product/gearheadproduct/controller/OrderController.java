package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.service.OrderService;
import ite.product.gearheadproduct.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    public ImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<List<Order>> get(){
        return ResponseEntity.ok(orderService.getAll());
    }
    @PostMapping
    public ResponseEntity<Order> post(@RequestParam("name") String name,
                                      @RequestParam("source") String source,
                                      @RequestPart("image")MultipartFile file
                                      ) throws IOException {
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        Order order = new Order();
        order.setName(name);
        order.setSource(source);
        order.setImagePath(file.getOriginalFilename());
        Order postingOrder = orderService.posting(order);
        return ResponseEntity.ok(postingOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        Order order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = orderService.deleteById(id);
        return ResponseEntity.ok(message);
    }

}
