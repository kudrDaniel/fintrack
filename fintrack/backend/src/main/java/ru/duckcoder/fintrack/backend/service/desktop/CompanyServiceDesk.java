package ru.duckcoder.fintrack.backend.service.desktop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.config.EntityManagerProvider;
import ru.duckcoder.fintrack.backend.dao.person.company.CompanyDAO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyUpdateDTO;
import ru.duckcoder.fintrack.backend.mapper.CompanyMapper;
import ru.duckcoder.fintrack.backend.model.person.company.Company;
import ru.duckcoder.fintrack.backend.service.CompanyService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class CompanyServiceDesk implements CompanyService {
    @PersistenceContext
    private EntityManager entityManager;
    private final CompanyDAO dao;
    private final CompanyMapper mapper;

    public CompanyServiceDesk() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
        this.dao = new CompanyDAO(this.entityManager);
        this.mapper = new CompanyMapper(this.entityManager);
    }

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
}
