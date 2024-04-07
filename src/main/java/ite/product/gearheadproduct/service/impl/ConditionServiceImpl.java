package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Condition;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.ConditionRepository;
import ite.product.gearheadproduct.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionRepository conditionRepository;
    @Override
    public List<Condition> getCondition() {
        return conditionRepository.findAll();
    }
    @Override
    public Condition postCondition(Condition condition) {
        return conditionRepository.save(condition);
    }
    @Override
    public String deleteCondition(Long id) {
        Condition condition = conditionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Condition"));
        conditionRepository.delete(condition);
        String message = "Delete Successfully";
        return message;
    }

    @Override
    public Condition findById(Long id) {
        Condition condition = conditionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Condition"));
        return condition;
    }
}
