package ru.desinfection.site.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CurrOrderInfoDTO {

    private BigDecimal totalCost;
    private Integer amountOfItems;
}
