package ru.duckcoder.fintrack.dto.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.duckcoder.fintrack.dto.AbstractDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class AccountDTO extends AbstractDTO {
    private final long id;
    private final String username;
    private final List<PersonDTO> persons;

    private String expandPersons() {
        if (persons.isEmpty())
            return "";
        return persons.stream()
                .map(personDTO -> "{" + personDTO.toString() + "}")
                .collect(Collectors.joining(","));
    }

    public String toExpandedString() {
        return "id:" + this.id
                + ",username:" + this.username
                + ",persons:[" + this.expandPersons() + "]";
    }

    @Override
    public String toString() {
        return "id:" + this.id
                + ",username:" + this.username
                + ",persons:[" + this.persons.getClass().getSimpleName() + "]";
    }
}
