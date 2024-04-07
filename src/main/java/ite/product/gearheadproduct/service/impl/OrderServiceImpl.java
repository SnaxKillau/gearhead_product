package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Order;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.OrderRepository;
import ite.product.gearheadproduct.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order posting(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long Id) {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFound(Id , "Order"));
        return order;
    }

    @Override
    public String deleteById(Long Id) {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFound(Id , "Order"));
        orderRepository.delete(order);
        String message = "Delete Successfully";
        return message;
    }
}
