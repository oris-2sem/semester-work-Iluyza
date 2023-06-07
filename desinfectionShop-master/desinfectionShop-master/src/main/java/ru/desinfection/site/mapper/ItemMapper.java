package ru.desinfection.site.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.desinfection.site.DTO.ItemCreateDTO;
import ru.desinfection.site.config.MapperConfig;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Pest;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperConfig.class)
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "picturePath", ignore = true)
    @Mapping(target = "pests", source = "pestsList")
    @Mapping(target = "amountSold", constant = "0")
    Item toItem(ItemCreateDTO itemCreateDTO, List<Pest> pestsList);

    @Mapping(target = "pests", source = "pests", qualifiedByName = "mapPests")
    @Mapping(target = "itemImage", ignore = true)
    ItemCreateDTO toItemCreateDTO(Item item);

    @Named(value = "mapPests")
    default List<String> mapPests(List<Pest> pests) {
        List<String> pestList = new ArrayList<>();
        for (Pest pest : pests) {
            pestList.add(pest.getName());
        }
        return pestList;
    }
}
