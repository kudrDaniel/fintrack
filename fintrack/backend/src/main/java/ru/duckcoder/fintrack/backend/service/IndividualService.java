package ru.duckcoder.fintrack.backend.service;

import ru.duckcoder.fintrack.core.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualUpdateDTO;

public interface IndividualService extends AbstractService<IndividualDTO, IndividualCreateDTO, IndividualUpdateDTO, Long> {
}
