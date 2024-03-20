package ru.duckcoder.fintrack.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractCreateDTO;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;

@Getter
@RequiredArgsConstructor
public abstract class PersonCreateDTO extends AbstractCreateDTO {
    private final Long accountId;
}
