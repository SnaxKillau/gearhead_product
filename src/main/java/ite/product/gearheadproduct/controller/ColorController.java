package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Color;
import ite.product.gearheadproduct.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> get(){
        List<Color> color = colorService.getColor();
        return ResponseEntity.ok(color);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id){
        Color colorById = colorService.getColorById(id);
        return ResponseEntity.ok(colorById);
    }
    @PostMapping("/admin")
    public ResponseEntity<Color> post(@RequestBody Color color){
        Color postingColor = colorService.postColor(color);
        return ResponseEntity.ok(postingColor);
    }
    @PatchMapping("/admin/{id}")
    public ResponseEntity<Color> update(@PathVariable Long id , @RequestBody Color color){
        Color updateColor = colorService.updateColor(id, color);
        return ResponseEntity.ok(updateColor);
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            return ResponseEntity.ok(colorService.deleteColor(id));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete color due to associated relationships");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete color");
        }

    }
}
