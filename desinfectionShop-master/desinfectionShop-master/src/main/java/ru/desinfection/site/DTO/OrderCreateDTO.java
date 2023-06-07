package ru.desinfection.site.DTO;

import lombok.Data;
import ru.desinfection.site.DTO.enums.Status;

import java.math.BigDecimal;

@Data
public class OrderCreateDTO {

    private String shippingAddress;

    private String comment;

}
