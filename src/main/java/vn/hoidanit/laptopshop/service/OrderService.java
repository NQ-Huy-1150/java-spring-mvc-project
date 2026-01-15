package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public Order handleSaveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public void deleteOrder(long id) {
        Optional<Order> optionalOrder = getOrderById(id);
        if (optionalOrder.isPresent()) {
            Order currentOrder = optionalOrder.get();
            List<OrderDetail> orderDetails = currentOrder.getOrderDetails();
            if (orderDetails != null) {
                // b1 : xoa orderDetail
                for (OrderDetail orderDetail : orderDetails) {
                    this.orderDetailRepository.deleteById(orderDetail.getId());
                }
                // b2 : xoa order
                this.orderRepository.deleteById(currentOrder.getId());
            }
        }

    }

    public void updateOrder(Order order) {
        Optional<Order> optionalOrder = getOrderById(order.getId());
        if (optionalOrder.isPresent()) {
            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(order.getStatus());
            handleSaveOrder(updateOrder);
        }
    }
}
