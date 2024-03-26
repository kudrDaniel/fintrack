package ru.duckcoder.fintrack.core.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.AbstractUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

@Getter
public class UserUpdateDTO extends AbstractUpdateDTO {
    private final Nullable<String> username;
    private final Nullable<String> password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserUpdateDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        if (username == null)
            this.username = null;
        else if (username.equals("null"))
            this.username = Nullable.empty();
        else
            this.username = Nullable.of(username);

        if (password == null)
            this.password = null;
        else if (password.equals("null"))
            this.password = Nullable.empty();
        else
            this.password = Nullable.of(password);
    }
}
