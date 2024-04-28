package ru.shopservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shopservice.dto.OrderDto;
import ru.shopservice.dto.OrderFilter;
import ru.shopservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/sort")
    public List<OrderDto> getOrdersSorted(OrderFilter orderFilter) {
        return orderService.getWithFilter(orderFilter);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable Integer id, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }
}
