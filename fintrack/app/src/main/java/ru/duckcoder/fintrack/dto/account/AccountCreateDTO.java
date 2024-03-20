package ru.duckcoder.fintrack.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractCreateDTO;
import ru.duckcoder.fintrack.dto.AbstractDTO;

@Getter
@RequiredArgsConstructor
public class AccountCreateDTO extends AbstractCreateDTO {
    private final String username;
    private final String password;
}
