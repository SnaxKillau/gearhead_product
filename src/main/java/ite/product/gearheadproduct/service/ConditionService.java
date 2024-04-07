package ite.product.gearheadproduct.service;



import ite.product.gearheadproduct.entity.Condition;

import java.util.List;

public interface ConditionService {
    public List<Condition> getCondition();
    public Condition postCondition(Condition condition);
    public String deleteCondition(Long id);

    public Condition findById(Long id);

}
