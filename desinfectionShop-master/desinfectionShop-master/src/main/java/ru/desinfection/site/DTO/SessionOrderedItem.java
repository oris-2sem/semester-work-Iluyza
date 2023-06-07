package ru.desinfection.site.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionOrderedItem {

    private Long id;
    private int amount;
}
