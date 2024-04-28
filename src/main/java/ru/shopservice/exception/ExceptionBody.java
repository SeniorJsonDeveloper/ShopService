package ru.shopservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionBody {

    private String message;

    private String description;
}
