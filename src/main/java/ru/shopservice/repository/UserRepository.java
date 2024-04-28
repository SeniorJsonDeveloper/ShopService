package ru.shopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shopservice.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String email);
}
