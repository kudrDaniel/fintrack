package ru.duckcoder.fintrack.backend.service.def;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.config.EntityManagerProvider;
import ru.duckcoder.fintrack.backend.dao.person.individual.IndividualDAO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.core.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.backend.mapper.IndividualMapper;
import ru.duckcoder.fintrack.backend.model.person.individual.Individual;
import ru.duckcoder.fintrack.backend.service.IndividualService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class IndividualServiceDef implements IndividualService {
    @PersistenceContext
    private  EntityManager entityManager;
    private final IndividualDAO dao;
    private final IndividualMapper mapper;

    private IndividualServiceDef() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
        this.dao = new IndividualDAO(this.entityManager);
        this.mapper = new IndividualMapper(this.entityManager);
    }

    @Override
    public IndividualDTO create(IndividualCreateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Individual model = this.mapper.map(dto);
            model = this.dao.save(model);
            transaction.commit();
            return this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IndividualDTO> readAll() {
        try {
            List<Individual> models = this.dao.findAll();
            return models.stream()
                    .map(this.mapper::map)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IndividualDTO read(Long id) {
        try {
            Individual model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Individual with id:" + id + " not found"));
            return this.mapper.map(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IndividualDTO update(Long id, IndividualUpdateDTO dto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Individual individualModel = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Individual with id:" + id + " not found"));
            this.mapper.update(dto, individualModel);
            individualModel = this.dao.save(individualModel);
            transaction.commit();
            return this.mapper.map(individualModel);
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
