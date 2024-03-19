package ru.duckcoder.fintrack.mapper;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.model.Account;
import ru.duckcoder.fintrack.model.Company;
import ru.duckcoder.fintrack.model.Individual;
import ru.duckcoder.fintrack.model.Person;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AccountMapper extends AbstractMapper<
        AccountDTO,
        AccountCreateDTO,
        AccountUpdateDTO,
        Account> {
    @Override
    public Account map(AccountCreateDTO dto) {
        return Account.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    @Override
    public AccountDTO map(Account model) {
        if (model == null)
            return null;
        List<PersonDTO> personDTOs = new ArrayList<>();
        AccountDTO dto = new AccountDTO(model.getId(), model.getUsername(), personDTOs);
        for (Person personModel : model.getPersons()) {
            PersonDTO personDTO;
            if (personModel instanceof Individual)
                personDTO = new IndividualDTO(personModel.getId(), dto, personModel.getLabel(),
                        ((Individual) personModel).getFirstName(),
                        ((Individual) personModel).getLastName(),
                        ((Individual) personModel).getLastName());
            else if (personModel instanceof Company)
                personDTO = new CompanyDTO(personModel.getId(), dto, personModel.getLabel(),
                        ((Company) personModel).getCompanyType(),
                        ((Company) personModel).getName());
            else
                personDTO = null;
            personDTOs.add(personDTO);
        }
        return dto;
    }

    @Override
    public void update(AccountUpdateDTO dto, Account model) {
        if (dto.getUsername() != null) {
            String newUsername;
            if (dto.getUsername().isPresent()) {
                newUsername = dto.getUsername().get();
                log.debug("Username set to:" + newUsername);
            }
            else {
                log.warn("Username can not be null");
                newUsername = model.getUsername();
                log.debug("Username unchanged");
            }
            model.setUsername(newUsername);
        } else
            log.debug("Username unchanged");
        if (dto.getPassword() != null) {
            String newPassword;
            if (dto.getPassword().isPresent()) {
                newPassword = dto.getPassword().get();
                log.debug("Password set to: ***");
            }
            else {
                log.warn("Password can not be null.");
                newPassword = model.getPassword();
                log.debug("Password unchanged");
            }
            model.setPassword(newPassword);
        } else {
            log.debug("Password unchanged");
        }
    }
}
