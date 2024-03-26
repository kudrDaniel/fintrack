package ru.duckcoder.fintrack.core.dto.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.AbstractDTO;

@Getter
public class PersonDTO extends AbstractDTO {
    private final long id;
    private final UserDTO account;
    private final String label;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PersonDTO(
            @JsonProperty(value = "id", required = true) long id,
            @JsonProperty(value = "account", required = false) UserDTO account,
            @JsonProperty(value = "label", required = true) String label
    ) {
        this.id = id;
        this.account = account;
        this.label = label;
    }

    @Override
    public String toString() {
        return "id:" + this.id
                + ",account:" + this.account
                + ",label:" + this.label;
    }
}
