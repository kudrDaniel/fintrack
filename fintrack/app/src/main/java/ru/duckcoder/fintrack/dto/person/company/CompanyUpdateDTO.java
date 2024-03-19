package ru.duckcoder.fintrack.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonUpdateDTO;
import ru.duckcoder.nullable.Nullable;

@Getter
public class CompanyUpdateDTO extends PersonUpdateDTO {
    public CompanyUpdateDTO(Nullable<Long> accountId) {
        super(accountId);
    }
}
