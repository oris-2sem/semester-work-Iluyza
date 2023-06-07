package ru.desinfection.site.DTO;

import lombok.Data;
import ru.desinfection.site.DTO.enums.Status;

@Data
public class OrderUpdateDTO {
    Status status;
}
