package ru.shopservice.dto;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {

    private String username;

    private String password;
}
