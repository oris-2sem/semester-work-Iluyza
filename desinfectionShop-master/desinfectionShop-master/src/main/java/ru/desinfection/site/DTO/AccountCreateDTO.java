package ru.desinfection.site.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateDTO {

    private String email;

    @ToString.Exclude
    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

}
