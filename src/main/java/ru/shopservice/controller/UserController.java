package ru.shopservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shopservice.dto.UserInputDto;
import ru.shopservice.dto.UserOutDto;
import ru.shopservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserOutDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserOutDto getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public UserOutDto createUser(@RequestBody UserInputDto userInput) {
        return userService.createUser(userInput);
    }

    @PutMapping("/{id}")
    public UserOutDto updateUser(@PathVariable Integer id, @RequestBody UserInputDto userInput) {
        return userService.updateUser(id, userInput);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
