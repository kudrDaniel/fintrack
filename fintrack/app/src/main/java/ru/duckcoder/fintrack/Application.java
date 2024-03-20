package ru.duckcoder.fintrack;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.service.account.AccountService;
import ru.duckcoder.fintrack.service.person.company.CompanyService;
import ru.duckcoder.fintrack.service.person.individual.IndividualService;
import ru.duckcoder.fintrack.service.person.PersonService;
import ru.duckcoder.nullable.Nullable;

import java.util.List;

@Log4j2
public class Application {
    public static void main(String[] args) {
        log.info("Entry point reached.");

        AccountCreateDTO accountCreateDTO0 = new AccountCreateDTO("user", "password");
        AccountCreateDTO accountCreateDTO1 = new AccountCreateDTO("lector", "12345");

        try (AccountService accountService = new AccountService();
             PersonService personService = new PersonService();
             IndividualService individualService = new IndividualService();
             CompanyService companyService = new CompanyService()) {
            AccountDTO accountDTO0 = accountService.create(accountCreateDTO0);
            AccountDTO accountDTO1 = accountService.create(accountCreateDTO1);
            AccountUpdateDTO accountUpdateDTO0 = new AccountUpdateDTO(Nullable.of("john"), null);
            accountDTO0 = accountService.update(accountDTO0.getId(), accountUpdateDTO0);

            IndividualCreateDTO individualCreateDTO0 = new IndividualCreateDTO("Danil", "Kudrickiy");
            IndividualCreateDTO individualCreateDTO1 = new IndividualCreateDTO(accountDTO0.getId(),"John", "Silver", "Paulson");
            IndividualDTO individualDTO0 = individualService.create(individualCreateDTO0);
            IndividualDTO individualDTO1 = individualService.create(individualCreateDTO1);

            List<AccountDTO> accountDTOs = accountService.readAll();
            accountDTOs.forEach(log::debug);

            List<PersonDTO> personDTOS = personService.readAll();
            personDTOS.forEach(log::debug);

            IndividualUpdateDTO individualUpdateDTO0 = new IndividualUpdateDTO(Nullable.of(accountDTO1.getId()), null);
            individualDTO0 = individualService.update(individualDTO0.getId(), individualUpdateDTO0);

            IndividualDTO copyOdIndividualDTO0 = individualService.read(individualDTO0.getId());

            List<PersonDTO> personsByAccountDTOs = accountService.readAllPersons(accountDTO0.getId());
            personsByAccountDTOs.forEach(log::debug);

            personDTOS = personService.readAll();
            personDTOS.forEach(log::debug);

            accountDTOs = accountService.readAll();
            accountDTOs.forEach(log::debug);

            accountService.delete(accountDTO0.getId());
            accountDTOs = accountService.readAll();
            accountDTOs.forEach(log::debug);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
