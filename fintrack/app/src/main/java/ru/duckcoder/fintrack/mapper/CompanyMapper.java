package ru.duckcoder.fintrack.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.model.account.Account;
import ru.duckcoder.fintrack.model.person.company.Company;

@Log4j2
public class CompanyMapper extends PersonMapper<
        CompanyDTO,
        CompanyCreateDTO,
        CompanyUpdateDTO,
        Company> {
    public CompanyMapper(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Company map(CompanyCreateDTO dto) {
        Account accountModel = this.toEntity(Account.class, dto.getAccountId());
        Company companyModel = new Company();
        companyModel.setAccount(accountModel);
        companyModel.setCompanyType(dto.getCompanyType());
        companyModel.setCompanyName(dto.getCompanyName());
        return companyModel;
    }

    @Override
    public CompanyDTO map(Company model) {
        AccountDTO accountDTO = null;
        if (model.getAccount() != null)
            accountDTO = new AccountMapper(this.getEntityManager()).map(model.getAccount());
        return new CompanyDTO(model.getId(), accountDTO, model.getLabel(), model.getCompanyType(), model.getCompanyName());
    }

    @Override
    public void update(CompanyUpdateDTO dto, Company model) {
        super.update(dto, model);
    }
}
