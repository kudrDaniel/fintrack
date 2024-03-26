package ru.duckcoder.fintrack.core.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.AbstractDTO;

@Getter
public class UserDTO extends AbstractDTO {
    private final long id;
    private final String username;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDTO(
            @JsonProperty(value = "id", required = true) long id,
            @JsonProperty(value = "username", required = true) String username
    ) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "id:" + this.id
                + ",username:" + this.username;
    }
}
