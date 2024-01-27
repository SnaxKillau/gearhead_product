package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Color;
import ite.product.gearheadproduct.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> get(){
        List<Color> color = colorService.getColor();
        return ResponseEntity.ok(color);
    }
    @GetMapping("{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id){
        Color colorById = colorService.getColorById(id);
        return ResponseEntity.ok(colorById);
    }
    @PostMapping
    public ResponseEntity<Color> post(@RequestBody Color color){
        Color postingColor = colorService.postColor(color);
        return ResponseEntity.ok(postingColor);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable Long id , @RequestBody Color color){
        Color updateColor = colorService.updateColor(id, color);
        return ResponseEntity.ok(updateColor);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String deleteColor = colorService.deleteColor(id);
        return ResponseEntity.ok(deleteColor);
    }
}
