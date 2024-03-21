package ru.duckcoder.fintrack.core.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

@Getter
public class CompanyUpdateDTO extends PersonUpdateDTO {
    public CompanyUpdateDTO(Nullable<Long> accountId) {
        super(accountId);
    }
}
