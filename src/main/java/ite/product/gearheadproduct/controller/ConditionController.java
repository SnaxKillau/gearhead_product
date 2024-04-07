package ite.product.gearheadproduct.controller;


import ite.product.gearheadproduct.entity.Condition;
import ite.product.gearheadproduct.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    @GetMapping
    public ResponseEntity<List<Condition>> get() {
        List<Condition> conditions = conditionService.getCondition();
        return ResponseEntity.ok(conditions);
    }

    @PostMapping
    public ResponseEntity<Condition> post(@RequestBody Condition condition) {
        Condition postCondition = conditionService.postCondition(condition);
        return ResponseEntity.ok(postCondition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(conditionService.deleteCondition(id));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete condition due to associated relationships");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete condition");
        }
    }

}
