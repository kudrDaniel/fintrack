package ru.duckcoder.fintrack.core.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.core.util.CompanyType;

@Getter
public class CompanyDTO extends PersonDTO {
    private final CompanyType companyType;
    private final String companyName;

    public CompanyDTO(long id, AccountDTO account, String label, CompanyType companyType, String companyName) {
        super(id, account, label);
        this.companyType = companyType;
        this.companyName = companyName;
    }
}
