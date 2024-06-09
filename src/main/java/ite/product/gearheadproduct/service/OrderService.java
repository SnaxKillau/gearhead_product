package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public List<Order> getAll();
    public Order posting(OrderRequest order);
    public Order getById(Long Id);
    public String deleteById(Long Id , Long userId);

    public List<Order> orderByUserId(String id);

    public String deleteAdmin(Long id);




}
