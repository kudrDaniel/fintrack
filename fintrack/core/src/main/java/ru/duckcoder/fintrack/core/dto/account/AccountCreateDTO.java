package ru.duckcoder.fintrack.core.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.core.dto.AbstractCreateDTO;

@Getter
@RequiredArgsConstructor
public class AccountCreateDTO extends AbstractCreateDTO {
    private final String username;
    private final String password;
}
