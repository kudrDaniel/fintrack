package ru.duckcoder.fintrack.dto.person.individual;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;

@Getter
public class IndividualDTO extends PersonDTO {
    private final String firstName;
    private final String lastName;
    private final String fatherName;
    public IndividualDTO(long id, AccountDTO account, String label, String firstName, String lastName, String fatherName) {
        super(id, account, label);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
    }

    @Override
    public String toString() {
        String fatherNameString = this.fatherName == null
                ? "null"
                : this.fatherName;
        return super.toString()
                + ",firstName:" + this.firstName
                + ",lastName:" + this.lastName
                + ",fatherName:" + fatherNameString;
    }
}
