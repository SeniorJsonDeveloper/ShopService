package ru.shopservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shopservice.dto.OrderDto;
import ru.shopservice.entity.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDto toDto(OrderEntity orderEntity);

    OrderEntity toEntity(OrderDto orderDto);

    List<OrderDto> toDtoList(List<OrderEntity> orderEntities);

    List<OrderEntity> toEntityList(List<OrderDto> orderDto);
}
