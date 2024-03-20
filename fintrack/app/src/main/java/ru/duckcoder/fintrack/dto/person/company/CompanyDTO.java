package ru.duckcoder.fintrack.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonDTO;
import ru.duckcoder.fintrack.model.person.company.Company;

@Getter
public class CompanyDTO extends PersonDTO {
    private final Company.CompanyType companyType;
    private final String companyName;

    public CompanyDTO(long id, AccountDTO account, String label, Company.CompanyType companyType, String companyName) {
        super(id, account, label);
        this.companyType = companyType;
        this.companyName = companyName;
    }
}
