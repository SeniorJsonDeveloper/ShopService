package ru.shopservice.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.shopservice.dto.OrderFilter;
import ru.shopservice.entity.OrderEntity;

import java.math.BigDecimal;

public interface OrderSpecification {

    static Specification<OrderEntity> withFilter(OrderFilter orderFilter){
        return Specification.where(byName(orderFilter.getName()))
                .and(byPrice(orderFilter.getMinPrice(),orderFilter.getMaxPrice()))
                .and(byCategory(orderFilter.getCategory()));
    }

    static Specification<OrderEntity> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if(name == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<OrderEntity> byPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), maxPrice);
            }
            if (maxPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }
    static Specification<OrderEntity> byCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }
}
