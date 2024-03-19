package ru.duckcoder.fintrack.mapper;

import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.dto.person.PersonUpdateDTO;
import ru.duckcoder.fintrack.model.Person;

public abstract class PersonMapper<
        D extends PersonDTO,
        DC extends PersonCreateDTO,
        DU extends PersonUpdateDTO,
        M extends Person> extends AbstractMapper<D, DC, DU, M> {
}
