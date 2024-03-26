package ru.duckcoder.fintrack.core.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.AbstractCreateDTO;

@Getter
public class UserCreateDTO extends AbstractCreateDTO {
    private final String username;
    private final String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserCreateDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
