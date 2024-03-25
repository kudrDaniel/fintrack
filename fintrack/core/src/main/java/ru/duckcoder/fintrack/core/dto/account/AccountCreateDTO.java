package ru.duckcoder.fintrack.core.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractCreateDTO;

@Getter
public class AccountCreateDTO extends AbstractCreateDTO {
    private final String username;
    private final String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountCreateDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
