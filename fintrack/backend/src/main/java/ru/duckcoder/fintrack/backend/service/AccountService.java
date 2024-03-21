package ru.duckcoder.fintrack.backend.service;

import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

import java.util.List;

public interface AccountService extends AbstractService<AccountDTO, AccountCreateDTO, AccountUpdateDTO, Long> {
    List<PersonDTO> readAllPersons(Long id);
}
