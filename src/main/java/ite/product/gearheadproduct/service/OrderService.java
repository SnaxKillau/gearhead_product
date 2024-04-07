package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getAll();
    public Order posting(Order order);
    public Order getById(Long Id);
    public String deleteById(Long Id);



}
