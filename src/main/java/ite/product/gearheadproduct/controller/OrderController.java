package ite.product.gearheadproduct.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import ite.product.gearheadproduct.entity.Image;
import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.repository.ImageRepository;
import ite.product.gearheadproduct.request.OrderRequest;
import ite.product.gearheadproduct.service.OrderService;
import ite.product.gearheadproduct.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                                      @RequestParam("created") LocalDate created,
                                      @RequestParam("userId") String userId,
                                      @RequestParam("address") String address,
                                      @RequestPart("image")MultipartFile file
                                      ) throws IOException {
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        OrderRequest order = new OrderRequest();
        order.setAddress(address);
        order.setName(name);
        order.setSource(source);
        order.setImagePath(file.getOriginalFilename());
        order.setCreated(created);
        order.setUser_id(userId);
        Order postingOrder = orderService.posting(order);
        return ResponseEntity.ok(postingOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        Order order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id , @RequestParam Long userId){
        String message = orderService.deleteById(id , userId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/currentOrder/{userId}")
    public ResponseEntity<List<Order>> getOrderByUser(@PathVariable String userId){
        List<Order> orders = orderService.orderByUserId(userId);
        return ResponseEntity.ok(orders);

    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> adminDelete(@PathVariable Long id){
        String message = orderService.deleteAdmin(id);
        return ResponseEntity.ok(message);
    }
}
