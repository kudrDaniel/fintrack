package ru.duckcoder.fintrack.service;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.dao.person.individual.IndividualDAO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualCreateDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualDTO;
import ru.duckcoder.fintrack.dto.person.individual.IndividualUpdateDTO;
import ru.duckcoder.fintrack.mapper.IndividualMapper;
import ru.duckcoder.fintrack.model.Individual;

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

    private final IndividualDAO dao = new IndividualDAO(this.getEntityManager());
    private final IndividualMapper mapper = new IndividualMapper();

    @Override
    public IndividualDTO create(IndividualCreateDTO dto) {
        Individual model = this.mapper.map(dto);
        model = this.dao.save(model);
        return this.mapper.map(model);
    }

    @Override
    public List<IndividualDTO> read() {
        List<Individual> models = this.dao.findAll();
        return models.stream()
                .map(this.mapper::map)
                .toList();
    }

    @Override
    public IndividualDTO read(Long id) {
        Individual model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Individual with id:" + id + " not found"));
        return this.mapper.map(model);
    }

    @Override
    public IndividualDTO update(Long id, IndividualUpdateDTO dto) {
        Individual model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("Individual with id:" + id + " not found"));
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
