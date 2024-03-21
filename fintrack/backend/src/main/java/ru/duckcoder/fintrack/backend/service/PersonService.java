package ru.duckcoder.fintrack.backend.service;

import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonUpdateDTO;

public interface PersonService extends AbstractService<PersonDTO, PersonCreateDTO, PersonUpdateDTO, Long> {
}
