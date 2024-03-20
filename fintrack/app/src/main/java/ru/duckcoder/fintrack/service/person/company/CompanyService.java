package ru.duckcoder.fintrack.service.person.company;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.company.CompanyDAO;
import ru.duckcoder.fintrack.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.mapper.CompanyMapper;
import ru.duckcoder.fintrack.model.person.company.Company;
import ru.duckcoder.fintrack.service.AbstractService;

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

    @PersistenceContext
    private final EntityManager entityManager = super.getEntityManager();
    private final CompanyDAO dao = new CompanyDAO(this.entityManager);
    private final CompanyMapper mapper = new CompanyMapper(this.entityManager);

    @Override
    public CompanyDTO create(CompanyCreateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Company model = this.mapper.map(dto);
            model = this.dao.save(model);
            transaction.commit();
            return this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CompanyDTO> readAll() {
        try {
            List<Company> models = this.dao.findAll();
            return models.stream()
                    .map(this.mapper::map)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompanyDTO read(Long id) {
        try {
            Company model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Company with id:" + id + " not found"));
            return this.mapper.map(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompanyDTO update(Long id, CompanyUpdateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Company model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Company with id:" + id + " not found"));
            this.mapper.update(dto, model);
            model = this.dao.save(model);
            transaction.commit();
            return this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.dao.deleteById(id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.entityManager.close();
        super.close();
    }
}
