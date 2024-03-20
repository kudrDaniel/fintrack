package ru.duckcoder.fintrack.service.person.individual;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.individual.IndividualDAO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.mapper.IndividualMapper;
import ru.duckcoder.fintrack.model.account.Account;
import ru.duckcoder.fintrack.model.person.individual.Individual;
import ru.duckcoder.fintrack.service.AbstractService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class IndividualService extends AbstractService<IndividualDTO, IndividualCreateDTO, IndividualUpdateDTO, Long> {
    private static volatile IndividualService instance;

    public static IndividualService getInstance() {
        IndividualService localInstance = instance;
        if (localInstance == null) {
            synchronized (IndividualService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new IndividualService();
            }
        }
        return localInstance;
    }

    @PersistenceContext
    private final EntityManager entityManager = this.getEntityManager();
    private final IndividualDAO dao = new IndividualDAO(this.entityManager);
    private final IndividualMapper mapper = new IndividualMapper(this.entityManager);

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

    @Override
    public void close() throws Exception {
        this.entityManager.close();
        super.close();
    }
}
