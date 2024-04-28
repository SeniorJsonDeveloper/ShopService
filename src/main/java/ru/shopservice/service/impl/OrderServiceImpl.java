package ru.shopservice.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shopservice.configuration.CacheProperties;
import ru.shopservice.dto.OrderDto;
import ru.shopservice.dto.OrderFilter;
import ru.shopservice.entity.OrderEntity;
import ru.shopservice.exception.NotFoundException;
import ru.shopservice.mapper.OrderMapper;
import ru.shopservice.mapper.UserMapper;
import ru.shopservice.repository.OrderRepository;
import ru.shopservice.repository.specification.OrderSpecification;
import ru.shopservice.service.OrderService;
import ru.shopservice.service.UserService;
import java.text.MessageFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final UserService userService;

    private final UserMapper userMapper;


    @Override
    @CacheEvict(cacheNames = CacheProperties.CacheNames.ORDERS,allEntries = true)
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    @Override
    @CacheEvict(cacheNames = CacheProperties.CacheNames.ORDER_BY_FILTER,allEntries = true)
    public List<OrderDto> getWithFilter(OrderFilter orderFilter) {
        return orderMapper.toDtoList(
                orderRepository.findAll(OrderSpecification.withFilter(orderFilter),
                PageRequest.of(orderFilter.getPageSize(),orderFilter.getPageNumber())).getContent());
    }

    @Override
    @CacheEvict(cacheNames = CacheProperties.CacheNames.ORDER_BY_ID,allEntries = true)
    public OrderDto getById(Integer id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(()->new NotFoundException(MessageFormat.format("Order with id {0} not found",id))));
    }

    @Override
    @CacheEvict(cacheNames = CacheProperties.CacheNames.NEW_ORDER,allEntries = true)
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderEntity.getId());
        orderEntity.setName(orderDto.getName());
        orderEntity.setCategory(orderDto.getCategory());
        orderEntity.setPrice(orderDto.getPrice());
        orderEntity.setCreatedAt(orderDto.getCreatedAt());
        orderEntity.setUpdatedAt(orderDto.getUpdatedAt());
        orderEntity.setUser(userMapper.toEntity(userService.getUserById(orderDto.getUserId())));
        orderRepository.save(orderEntity);
        OrderDto orderOutDto = new OrderDto();
        orderOutDto.setId(orderEntity.getId());
        orderOutDto.setName(orderEntity.getName());
        orderDto.setCategory(orderEntity.getCategory());
        orderDto.setPrice(orderEntity.getPrice());
        orderDto.setUserId(orderEntity.getUser().getId());
        return orderOutDto;
    }

    @Override
    @CacheEvict(cacheNames = CacheProperties.CacheNames.NEW_ORDER,allEntries = true)
    @Transactional
    public OrderDto updateOrder(Integer id, OrderDto orderDto) {
        OrderEntity order = orderMapper.toEntity(getById(id));
        if(!order.getName().equals(orderDto.getName())) {
            order.setName(orderDto.getName());
        }
        if(!order.getCategory().equals(order.getName())) {
            order.setCategory(orderDto.getCategory());
        }
        if (!order.getPrice().equals(orderDto.getPrice())) {
            order.setPrice(orderDto.getPrice());
        }
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Cacheable(cacheNames = CacheProperties.CacheNames.ORDER_BY_ID)
    public void deleteOrder(Integer id) {
        OrderEntity order = orderMapper.toEntity(getById(id));
        if (order==null){
            throw new NotFoundException(MessageFormat.format("Order with id {0} not found",id));
        }
        orderRepository.delete(order);

    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        orderRepository.deleteAllById(ids);
    }
}
