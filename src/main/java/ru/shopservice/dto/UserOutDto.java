package ru.shopservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDto {

    private Integer id;

    private String username;

    private Instant createdAt;

    @JsonIgnore
    private String password;


}
