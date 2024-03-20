package ru.duckcoder.fintrack.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;

@Getter
@RequiredArgsConstructor
public class AccountDTO extends AbstractDTO {
    private final long id;
    private final String username;

    @Override
    public String toString() {
        return "id:" + this.id
                + ",username:" + this.username;
    }
}
