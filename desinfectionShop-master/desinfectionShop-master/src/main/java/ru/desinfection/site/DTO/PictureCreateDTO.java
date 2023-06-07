package ru.desinfection.site.DTO;

import lombok.Data;
import ru.desinfection.site.entity.Item;

@Data
public class PictureCreateDTO {

    private Item item;

    private String path;
}
