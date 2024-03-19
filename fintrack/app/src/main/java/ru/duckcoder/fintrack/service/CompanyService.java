package ru.duckcoder.fintrack.service;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.company.CompanyDAO;
import ru.duckcoder.fintrack.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.mapper.CompanyMapper;
import ru.duckcoder.fintrack.model.Company;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class CompanyService extends AbstractService<CompanyDTO, CompanyCreateDTO, CompanyUpdateDTO, Long> {
    private static volatile CompanyService instance;

    public static CompanyService getInstance() {
        CompanyService localService = instance;
        if (instance == null) {
            synchronized (CompanyService.class) {
                localService = instance;
                if (localService == null)
                    localService = instance = new CompanyService();
            }
        }
        return localService;
    }

    private final CompanyDAO dao = new CompanyDAO(this.getEntityManager());
    private final CompanyMapper mapper = new CompanyMapper();

    @Override
    public CompanyDTO create(CompanyCreateDTO dto) {
        Company model = this.mapper.map(dto);
        model = this.dao.save(model);
        return this.mapper.map(model);
    }

    @Override
    public List<CompanyDTO> read() {
        List<Company> models = this.dao.findAll();
        return models.stream()
                .map(this.mapper::map)
                .toList();
    }

    @Override
    public CompanyDTO read(Long id) {
        Company model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Company with id:" + id + " not found"));
        return this.mapper.map(model);
    }

    @Override
    public CompanyDTO update(Long id, CompanyUpdateDTO dto) {
        Company model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Company with id:" + id + " not found"));
        this.mapper.update(dto, model);
        model = this.dao.save(model);
        return this.mapper.map(model);
    }

    @Override
    public void delete(Long id) {
        this.dao.deleteById(id);
    }

    @Override
    public void close() throws Exception {
        this.dao.close();
        super.close();
    }
}
