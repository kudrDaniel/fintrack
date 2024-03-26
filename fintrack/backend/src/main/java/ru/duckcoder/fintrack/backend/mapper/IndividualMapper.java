package ru.duckcoder.fintrack.backend.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.backend.model.user.User;
import ru.duckcoder.fintrack.backend.model.person.individual.Individual;

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
        User userModel = this.toEntity(User.class, dto.getAccountId());
        Individual individualModel = new Individual();
        individualModel.setUser(userModel);
        individualModel.setFirstName(dto.getFirstName());
        individualModel.setLastName(dto.getLastName());
        individualModel.setFatherName(dto.getFatherName());
        return individualModel;
    }

    @Override
    public IndividualDTO map(Individual model) {
        UserDTO userDTO = null;
        if (model.getUser() != null)
            userDTO = new UserMapper(this.getEntityManager()).map(model.getUser());
        return new IndividualDTO(model.getId(), userDTO, model.getLabel(), model.getFirstName(), model.getLastName(), model.getFatherName());
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
