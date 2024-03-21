package ru.duckcoder.fintrack.core.dto.person.individual;

import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;

@Getter
public class IndividualCreateDTO extends PersonCreateDTO {
    private final String firstName;
    private final String lastName;
    private final String fatherName;

    public IndividualCreateDTO(String firstName, String lastName) {
        this(firstName, lastName, null);
    }
    public IndividualCreateDTO(String firstName, String lastName, String fatherName) {
        this(null, firstName, lastName, fatherName);
    }
    public IndividualCreateDTO(Long accountId,
                               String firstName,
                               String lastName) {
        this(accountId, firstName, lastName, null);
    }
    public IndividualCreateDTO(Long accountId,
                               String firstName,
                               String lastName,
                               String fatherName) {
        super(accountId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
    }
}
