package ru.duckcoder.fintrack.backend.controller;

import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

import java.util.List;

public interface AccountController {
     List<PersonDTO> readAllPersonsByID(Long id);
     AccountDTO create(AccountCreateDTO dto);
     AccountDTO read(Long id);
     List<AccountDTO> readAll();
     AccountDTO update(Long id, AccountUpdateDTO dto);
     void delete(Long id);
}
