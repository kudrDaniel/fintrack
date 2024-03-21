package ru.duckcoder.fintrack.core.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

@Getter
@RequiredArgsConstructor
public abstract class PersonUpdateDTO extends AbstractUpdateDTO {
    private final Nullable<Long> accountId;
}
