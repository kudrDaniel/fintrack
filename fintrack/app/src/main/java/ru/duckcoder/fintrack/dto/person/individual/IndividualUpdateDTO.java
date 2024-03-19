package ru.duckcoder.fintrack.dto.person.individual;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonUpdateDTO;
import ru.duckcoder.nullable.Nullable;

@Getter
public class IndividualUpdateDTO extends PersonUpdateDTO {
    private final Nullable<String> lastName;

    public IndividualUpdateDTO(Nullable<Long> accountId, Nullable<String> lastName) {
        super(accountId);
        this.lastName = lastName;
    }
}
