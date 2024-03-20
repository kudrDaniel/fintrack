package ru.duckcoder.fintrack.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.AbstractUpdateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.nullable.Nullable;

@Getter
@RequiredArgsConstructor
public abstract class PersonUpdateDTO extends AbstractUpdateDTO {
    private final Nullable<Long> accountId;
}
