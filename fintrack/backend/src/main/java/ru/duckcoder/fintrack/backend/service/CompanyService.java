package ru.duckcoder.fintrack.backend.service;

import ru.duckcoder.fintrack.core.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyUpdateDTO;

public interface CompanyService extends AbstractService<CompanyDTO, CompanyCreateDTO, CompanyUpdateDTO, Long> {
}
