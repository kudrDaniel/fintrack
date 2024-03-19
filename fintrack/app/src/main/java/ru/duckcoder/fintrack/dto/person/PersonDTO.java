package ru.duckcoder.fintrack.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;

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
