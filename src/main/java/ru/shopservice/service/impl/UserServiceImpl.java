package ru.shopservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shopservice.dto.UserInputDto;
import ru.shopservice.dto.UserOutDto;
import ru.shopservice.entity.OrderEntity;
import ru.shopservice.entity.UserEntity;
import ru.shopservice.exception.BadRequestException;
import ru.shopservice.exception.NotFoundException;
import ru.shopservice.mapper.UserMapper;
import ru.shopservice.repository.OrderRepository;
import ru.shopservice.repository.UserRepository;
import ru.shopservice.service.OrderService;
import ru.shopservice.service.UserService;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private  OrderService orderService;

    @Autowired
    @Lazy
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<UserOutDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserOutDto getUserById(Integer id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(()->new NotFoundException
                        (MessageFormat.format("User with id {0} not found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public UserOutDto createUser(UserInputDto userInputDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userEntity.getId());
        userEntity.setUsername(userInputDto.getUsername());
        if (userRepository.existsByUsername(userInputDto.getUsername())) {
            throw new BadRequestException(MessageFormat.format("User with username {0} already exists", userInputDto.getUsername()));
        }
        userEntity.setPassword(userInputDto.getPassword());
        userEntity.setCreatedAt(Instant.now());
        userEntity.setOrders(null);
        userRepository.save(userEntity);
        UserOutDto userOutDto = new UserOutDto();
        userOutDto.setId(userEntity.getId());
        userOutDto.setUsername(userEntity.getUsername());
        userOutDto.setPassword(userEntity.getPassword());
        userOutDto.setCreatedAt(userEntity.getCreatedAt());
        return userOutDto;

    }

    @Transactional(readOnly = true)
    public UserOutDto updateUser(Integer id, UserInputDto userInputDto) {
        UserEntity user = userMapper.toEntity(getUserById(id));
        if(!user.getUsername().equals(userInputDto.getPassword())) {
            user.setUsername(userInputDto.getUsername());
        }
        if(!user.getPassword().equals(userInputDto.getPassword())) {
            user.setPassword(userInputDto.getPassword());
        }
        return userMapper.toDto(userRepository.save(user));

    }

    @Override
    public void deleteUser(Integer id) {
        UserEntity user = userMapper.toEntity(getUserById(id));
        if (user == null) {
            throw new NotFoundException(MessageFormat.format("User with id {0} not found", id));
        }
        orderService.deleteByIds(user.getOrders()
                .stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toList()));
        userRepository.deleteById(id);

    }
}
