package ru.duckcoder.fintrack.backend.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.backend.model.account.Account;
import ru.duckcoder.fintrack.backend.model.person.Person;

@Log4j2
public abstract class PersonMapper<
        D extends PersonDTO,
        DC extends PersonCreateDTO,
        DU extends PersonUpdateDTO,
        M extends Person> extends AbstractMapper<D, DC, DU, M> {
    protected PersonMapper(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected void update(DU dto, M model) {
        if (dto.getAccountId() != null) {
            if (dto.getAccountId().isPresent()) {
                Account accountModel = this.toEntity(Account.class, dto.getAccountId().get());
                if (accountModel != null)
                    model.setAccount(accountModel);
                else
                    log.debug("Account unchanged");
            } else
                model.setAccount(null);
        } else
            log.debug("Account unchanged");
    }
}
