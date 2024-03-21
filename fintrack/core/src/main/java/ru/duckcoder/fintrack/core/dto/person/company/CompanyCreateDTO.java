package ru.duckcoder.fintrack.core.dto.person.company;

import lombok.Getter;
import ru.duckcoder.fintrack.core.dto.person.PersonCreateDTO;
import ru.duckcoder.fintrack.core.util.CompanyType;

@Getter
public class CompanyCreateDTO extends PersonCreateDTO {
    private final CompanyType companyType;
    private final String companyName;

    public CompanyCreateDTO(String name) {
        this(CompanyType.EMPTY, name);
    }
    public CompanyCreateDTO(CompanyType companyType, String companyName) {
        this(null, companyType, companyName);
    }
    public CompanyCreateDTO(Long accountId, String companyName) {
        this(accountId, CompanyType.EMPTY, companyName);
    }
    public CompanyCreateDTO(Long accountId, CompanyType companyType, String companyName) {
        super(accountId);
        this.companyType = companyType;
        this.companyName = companyName;
    }
}
