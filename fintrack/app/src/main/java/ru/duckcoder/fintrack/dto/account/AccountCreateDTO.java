package ru.duckcoder.fintrack.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;

@Getter
@RequiredArgsConstructor
public class AccountCreateDTO extends AbstractDTO {
    private final String username;
    private final String password;
}
