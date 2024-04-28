package ru.shopservice.service;

import ru.shopservice.dto.UserInputDto;
import ru.shopservice.dto.UserOutDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserOutDto> getAllUsers();

    UserOutDto getUserById(Integer id);

    UserOutDto createUser(UserInputDto userInputDto);

    UserOutDto updateUser(Integer id,UserInputDto userInputDto);

    void deleteUser(Integer id);

}
