package ru.duckcoder.fintrack.core.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractCreateDTO;

@Getter
@RequiredArgsConstructor
public abstract class PersonCreateDTO extends AbstractCreateDTO {
    private final Long accountId;
}
