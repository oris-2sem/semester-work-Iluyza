package ru.desinfection.site.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.desinfection.site.DTO.AccountCreateDTO;
import ru.desinfection.site.config.MapperConfig;
import ru.desinfection.site.entity.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(config = MapperConfig.class)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true, qualifiedByName = "getTimeStamp")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "currOrder", ignore = true)
    Account toAccount(AccountCreateDTO accountCreateDTO);

    @Named(value = "getDate")
    private LocalDateTime getTimeStamp() {
        return LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy")));
    }

}
