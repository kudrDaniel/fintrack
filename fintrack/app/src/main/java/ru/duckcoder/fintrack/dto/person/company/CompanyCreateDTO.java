package ru.duckcoder.fintrack.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.model.person.company.Company;

@Getter
public class CompanyCreateDTO extends PersonCreateDTO {
    private final Company.CompanyType companyType;
    private final String companyName;

    public CompanyCreateDTO(String name) {
        this(Company.CompanyType.EMPTY, name);
    }
    public CompanyCreateDTO(Company.CompanyType companyType, String companyName) {
        this(null, companyType, companyName);
    }
    public CompanyCreateDTO(Long accountId, String companyName) {
        this(accountId, Company.CompanyType.EMPTY, companyName);
    }
    public CompanyCreateDTO(Long accountId, Company.CompanyType companyType, String companyName) {
        super(accountId);
        this.companyType = companyType;
        this.companyName = companyName;
    }
}
