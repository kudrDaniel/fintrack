package ru.duckcoder.fintrack.core.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractDTO;

@Getter
public class AccountDTO extends AbstractDTO {
    private final long id;
    private final String username;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountDTO(@JsonProperty("id") long id, @JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "id:" + this.id
                + ",username:" + this.username;
    }
}
