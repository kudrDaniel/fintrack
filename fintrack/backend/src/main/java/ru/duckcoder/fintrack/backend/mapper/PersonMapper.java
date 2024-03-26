package ru.duckcoder.fintrack.backend.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.backend.model.user.User;
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
                User userModel = this.toEntity(User.class, dto.getAccountId().get());
                if (userModel != null)
                    model.setUser(userModel);
                else
                    log.debug("User unchanged");
            } else
                model.setUser(null);
        } else
            log.debug("User unchanged");
    }
}
