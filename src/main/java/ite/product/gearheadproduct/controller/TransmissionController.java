package ite.product.gearheadproduct.controller;

import ite.product.gearheadproduct.entity.Transmission;
import ite.product.gearheadproduct.service.TransmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transmission")
public class TransmissionController {

    @Autowired
    private TransmissionService transmissionService;

    @GetMapping
    public ResponseEntity<List<Transmission>> get() {
        List<Transmission> transmission = transmissionService.getTransmission();
        return ResponseEntity.ok(transmission);
    }
    @PostMapping("/admin")
    public  ResponseEntity<Transmission> post(@RequestBody Transmission transmission) {
        Transmission postingTransmission = transmissionService.postTransmission(transmission);
        return ResponseEntity.ok(postingTransmission);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(transmissionService.deleteTransmission(id));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete transmission due to associated relationships");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete transmission");
        }

    }

}
