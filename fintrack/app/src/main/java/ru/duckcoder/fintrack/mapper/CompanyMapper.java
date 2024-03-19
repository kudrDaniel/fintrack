package ru.duckcoder.fintrack.mapper;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dto.account.AccountDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.model.Account;
import ru.duckcoder.fintrack.model.Company;
import ru.duckcoder.fintrack.service.AccountService;
import ru.duckcoder.fintrack.service.CompanyService;

@Log4j2
public class CompanyMapper extends PersonMapper<
        CompanyDTO,
        CompanyCreateDTO,
        CompanyUpdateDTO,
        Company> {
    @Override
    public Company map(CompanyCreateDTO dto) {
        Account accountModel = new AccountMapper().toEntity(dto.getAccountId(), Account.class, AccountService.getInstance());
        return Company.builder()
                .account(accountModel)
                .companyType(dto.getCompanyType())
                .name(dto.getName())
                .build();
    }

    @Override
    public CompanyDTO map(Company model) {
        AccountDTO accountDTO = new AccountMapper().map(model.getAccount());
        return new CompanyDTO(model.getId(), accountDTO, model.getLabel(), model.getCompanyType(), model.getName());
    }

    @Override
    public void update(CompanyUpdateDTO dto, Company model) {
        if (dto.getAccountId() != null) {
            if (dto.getAccountId().isPresent()) {
                Account newAccountModel = new AccountMapper().toEntity(dto.getAccountId().get(), Account.class, AccountService.getInstance());
                if (newAccountModel != null) {
                    model.setAccount(newAccountModel);
                    log.debug("Account set to:" + newAccountModel);
                } else
                    log.debug("Account unchanged");
            } else {
                model.setAccount(null);
                log.debug("Account unset");
            }
        } else
            log.debug("Account unchanged");
    }
}
