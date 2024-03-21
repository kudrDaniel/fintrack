package ru.duckcoder.fintrack.core.dto.person.individual;

import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

@Getter
public class IndividualUpdateDTO extends PersonUpdateDTO {
    private final Nullable<String> lastName;

    public IndividualUpdateDTO(Nullable<Long> accountId, Nullable<String> lastName) {
        super(accountId);
        this.lastName = lastName;
    }
}
