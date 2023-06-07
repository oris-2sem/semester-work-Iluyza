package ru.desinfection.site.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.desinfection.site.DTO.OrderCreateDTO;
import ru.desinfection.site.config.MapperConfig;
import ru.desinfection.site.entity.Order;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "account.id", ignore = true)
    @Mapping(target = "finalCost", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toOrder(OrderCreateDTO orderCreateDTO);
}
