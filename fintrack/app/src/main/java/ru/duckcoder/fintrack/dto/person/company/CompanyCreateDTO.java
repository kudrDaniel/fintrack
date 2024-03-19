package ru.duckcoder.fintrack.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.model.Company;

@Getter
public class CompanyCreateDTO extends PersonCreateDTO {
    private final Company.CompanyType companyType;
    private final String name;

    public CompanyCreateDTO(String name) {
        this(Company.CompanyType.EMPTY, name);
    }
    public CompanyCreateDTO(Company.CompanyType companyType, String name) {
        this(null, companyType, name);
    }
    public CompanyCreateDTO(Long accountId, String name) {
        this(accountId, Company.CompanyType.EMPTY, name);
    }
    public CompanyCreateDTO(Long accountId, Company.CompanyType companyType, String name) {
        super(accountId);
        this.companyType = companyType;
        this.name = name;
    }
}
