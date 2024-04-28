package ru.shopservice.service;

import ru.shopservice.dto.OrderDto;
import ru.shopservice.dto.OrderFilter;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    List<OrderDto> getWithFilter(OrderFilter orderFilter);

    OrderDto getById(Integer id);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(Integer id,OrderDto orderDto);

    void deleteOrder(Integer id);

    void deleteByIds(List<Integer> ids);


}
