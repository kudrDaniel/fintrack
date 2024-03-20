package ru.duckcoder.fintrack.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.AbstractUpdateDTO;
import ru.duckcoder.nullable.Nullable;

@Getter
@RequiredArgsConstructor
public class AccountUpdateDTO extends AbstractUpdateDTO {
    private final Nullable<String> username;
    private final Nullable<String> password;
}
