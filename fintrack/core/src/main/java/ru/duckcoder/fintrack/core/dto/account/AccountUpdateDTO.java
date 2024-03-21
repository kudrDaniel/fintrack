package ru.duckcoder.fintrack.core.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

@Getter
@RequiredArgsConstructor
public class AccountUpdateDTO extends AbstractUpdateDTO {
    private final Nullable<String> username;
    private final Nullable<String> password;
}
