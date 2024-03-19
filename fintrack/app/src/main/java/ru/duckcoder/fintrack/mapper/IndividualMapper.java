package ru.duckcoder.fintrack.mapper;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.model.Account;
import ru.duckcoder.fintrack.model.Individual;
import ru.duckcoder.fintrack.service.AccountService;

@Log4j2
public class IndividualMapper extends PersonMapper<
        IndividualDTO,
        IndividualCreateDTO,
        IndividualUpdateDTO,
        Individual> {
    @Override
    public Individual map(IndividualCreateDTO dto) {
        Account accountModel = new AccountMapper().toEntity(dto.getAccountId(), Account.class, AccountService.getInstance());
        return Individual.builder()
                .account(accountModel)
                .fullName(dto.getFirstName(), dto.getLastName(), dto.getFatherName())
                .build();
    }

    @Override
    public IndividualDTO map(Individual model) {
        AccountDTO accountDTO = new AccountMapper().map(model.getAccount());
        return new IndividualDTO(model.getId(), accountDTO, model.getLabel(), model.getFirstName(), model.getLastName(), model.getFatherName());
    }

    @Override
    public void update(IndividualUpdateDTO dto, Individual model) {
        if (dto.getAccountId() != null) {
            if (dto.getAccountId().isPresent()) {
                Account newAccountModel = new AccountMapper().toEntity(dto.getAccountId().get(), Account.class, AccountService.getInstance());
                if (newAccountModel != null) {
                    model.setAccount(newAccountModel);
                    log.debug("Account set to:" + newAccountModel);
                } else
                    log.debug("Account unchanged");
            } else {
                model.setAccount(null);
                log.debug("Account unset");
            }
        } else
            log.debug("Account unchanged");
        if (dto.getLastName() != null) {
            if (dto.getLastName().isPresent()) {
                String newLastName = dto.getLastName().get();
                model.setLastName(newLastName);
                log.debug("LastName set to:" + newLastName);
            } else {
                log.warn("LastName can not be replaced to null");
                log.debug("LastName unchanged");
            }
        } else
            log.debug("LastName unchanged");
    }
}
