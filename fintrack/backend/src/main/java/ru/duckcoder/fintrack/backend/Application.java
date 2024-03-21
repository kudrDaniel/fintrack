package ru.duckcoder.fintrack.backend;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.controller.AccountController;
import ru.duckcoder.fintrack.backend.controller.custom.AccountControllerDesk;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Application {
    public static void main(String[] args) throws Exception {
        log.debug("Entry point reached.");

        AccountCreateDTO accountCreateDTO0 = new AccountCreateDTO("user", "password");
        AccountCreateDTO accountCreateDTO1 = new AccountCreateDTO("lector", "12345");

        List<AccountDTO> accounts = new ArrayList<>();

        AccountController aController = new AccountControllerDesk();
        try {
            accounts.add(aController.create(accountCreateDTO0));
            accounts.add(aController.create(accountCreateDTO1));
            aController.update(1L, new AccountUpdateDTO(Nullable.of("kek"), Nullable.of("qwerty")));
            accounts = aController.readAll();
        } catch (Exception e) {
            log.error(e);
        }
        accounts.forEach(accountDTO -> log.debug(new StringBuilder().append('{').append(accountDTO).append('}')));
    }
}
