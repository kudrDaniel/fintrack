package ru.duckcoder.fintrack.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.model.account.Account;
import ru.duckcoder.fintrack.model.person.individual.Individual;

@Log4j2
public class IndividualMapper extends PersonMapper<
        IndividualDTO,
        IndividualCreateDTO,
        IndividualUpdateDTO,
        Individual> {
    public IndividualMapper(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Individual map(IndividualCreateDTO dto) {
        Account accountModel = this.toEntity(Account.class, dto.getAccountId());
        Individual individualModel = new Individual();
        individualModel.setAccount(accountModel);
        individualModel.setFirstName(dto.getFirstName());
        individualModel.setLastName(dto.getLastName());
        individualModel.setFatherName(dto.getFatherName());
        return individualModel;
    }

    @Override
    public IndividualDTO map(Individual model) {
        AccountDTO accountDTO = null;
        if (model.getAccount() != null)
            accountDTO = new AccountMapper(this.getEntityManager()).map(model.getAccount());
        return new IndividualDTO(model.getId(), accountDTO, model.getLabel(), model.getFirstName(), model.getLastName(), model.getFatherName());
    }

    @Override
    public void update(IndividualUpdateDTO dto, Individual model) {
        super.update(dto, model);
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
