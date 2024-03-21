package ru.duckcoder.fintrack.core.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.AbstractDTO;

@Getter
@RequiredArgsConstructor
public class PersonDTO extends AbstractDTO {
    private final long id;
    private final AccountDTO account;
    private final String label;

    @Override
    public String toString() {
        return "id:" + this.id
                + ",account:" + this.account
                + ",label:" + this.label;
    }
}
