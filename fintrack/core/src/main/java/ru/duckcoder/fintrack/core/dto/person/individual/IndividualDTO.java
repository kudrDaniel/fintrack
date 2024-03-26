package ru.duckcoder.fintrack.core.dto.person.individual;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

@Getter
public class IndividualDTO extends PersonDTO {
    private final String firstName;
    private final String lastName;
    private final String fatherName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public IndividualDTO(
            @JsonProperty(value = "id", required = true) long id,
            @JsonProperty(value = "account", required = false) UserDTO account,
            @JsonProperty(value = "label", required = true) String label,
            @JsonProperty(value = "firstName", required = true) String firstName,
            @JsonProperty(value = "lastName", required = true) String lastName,
            @JsonProperty(value = "fatherName", required = false) String fatherName) {
        super(id, account, label);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
    }

    @Override
    public String toString() {
        String fatherNameString = this.fatherName == null
                ? "null"
                : this.fatherName;
        return super.toString()
                + ",firstName:" + this.firstName
                + ",lastName:" + this.lastName
                + ",fatherName:" + fatherNameString;
    }
}
