package ru.duckcoder.fintrack.backend.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.backend.model.user.User;
import ru.duckcoder.fintrack.backend.model.person.company.Company;

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
        User userModel = this.toEntity(User.class, dto.getAccountId());
        Company companyModel = new Company();
        companyModel.setUser(userModel);
        companyModel.setCompanyType(dto.getCompanyType());
        companyModel.setCompanyName(dto.getCompanyName());
        return companyModel;
    }

    @Override
    public CompanyDTO map(Company model) {
        UserDTO userDTO = null;
        if (model.getUser() != null)
            userDTO = new UserMapper(this.getEntityManager()).map(model.getUser());
        return new CompanyDTO(model.getId(), userDTO, model.getLabel(), model.getCompanyType(), model.getCompanyName());
    }

    @Override
    public void update(CompanyUpdateDTO dto, Company model) {
        super.update(dto, model);
    }
}
