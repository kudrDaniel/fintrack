package ru.duckcoder.fintrack.backend.controller.custom;

import ru.duckcoder.fintrack.backend.config.DependencyProvider;
import ru.duckcoder.fintrack.backend.controller.AccountController;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.backend.service.AccountService;

import java.util.List;

public final class AccountControllerDesk implements AccountController {
    private AccountService getService() {
        return DependencyProvider.getInstance().getImplementation(AccountService.class);
    }

    @Override
    public List<PersonDTO> readAllPersonsByID(Long id) {
        return this.getService().readAllPersons(id);
    }

    @Override
    public AccountDTO create(AccountCreateDTO dto) {
        return this.getService().create(dto);
    }

    @Override
    public AccountDTO read(Long id) {
        return this.getService().read(id);
    }

    @Override
    public List<AccountDTO> readAll() {
        return this.getService().readAll();
    }

    @Override
    public AccountDTO update(Long id, AccountUpdateDTO dto) {
        return this.getService().update(id, dto);
    }

    @Override
    public void delete(Long id) {
        this.getService().delete(id);
    }
}
