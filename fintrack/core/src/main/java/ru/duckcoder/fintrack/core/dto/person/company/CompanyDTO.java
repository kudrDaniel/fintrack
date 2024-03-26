package ru.duckcoder.fintrack.core.dto.person.company;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.util.CompanyType;

@Getter
public class CompanyDTO extends PersonDTO {
    private final CompanyType companyType;
    private final String companyName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CompanyDTO(
            @JsonProperty(value = "id", required = true) long id,
            @JsonProperty(value = "account", required = false) UserDTO account,
            @JsonProperty(value = "label", required = true) String label,
            @JsonProperty(value = "companyType", required = true) CompanyType companyType,
            @JsonProperty(value = "companyName", required = true) String companyName
    ) {
        super(id, account, label);
        this.companyType = companyType;
        this.companyName = companyName;
    }
}
