package ru.duckcoder.fintrack.backend.service.def;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.dao.user.UserDAO;
import ru.duckcoder.fintrack.core.dto.user.UserCreateDTO;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.user.UserUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;
import ru.duckcoder.fintrack.backend.mapper.UserMapper;
import ru.duckcoder.fintrack.backend.model.user.User;
import ru.duckcoder.fintrack.backend.model.person.Person;
import ru.duckcoder.fintrack.backend.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class UserServiceDef implements UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserDAO dao;
    private final UserMapper mapper;

    public UserServiceDef(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.dao = new UserDAO(this.entityManager);
        this.mapper = new UserMapper(this.entityManager);
    }

    public List<PersonDTO> readAllPersons(Long id) {
        List<PersonDTO> result;
        try {
            User userModel = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("User with id:" + id + " not found"));
            log.debug("Found user with id:" + id);
            List<Person> personModels = this.dao.findAllPersons(id);
            UserDTO userDTO = mapper.map(userModel);
            result = personModels.stream()
                    .map(model -> {
                        this.entityManager.detach(model);
                        return new PersonDTO(model.getId(), userDTO, model.getLabel());
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public UserDTO create(UserCreateDTO dto) {
        UserDTO result;
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            User model = this.mapper.map(dto);
            model = this.dao.save(model);
            transaction.commit();
            result = this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<UserDTO> readAll() {
        List<UserDTO> result;
        try {
            List<User> models = this.dao.findAll();
            result = models.stream()
                    .map(this.mapper::map)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public UserDTO read(Long id) {
        UserDTO result;
        try {
            User model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("User with id:" + id + " not found"));
            log.debug("User account with id:" + id);
            result = this.mapper.map(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public UserDTO update(Long id, UserUpdateDTO dto) {
        UserDTO result;
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            User model = this.dao.findById(id).orElseThrow(() -> new NoSuchElementException("User with id:" + id + " not found"));
            this.mapper.update(dto, model);
            model = this.dao.save(model);
            transaction.commit();
            result = this.mapper.map(model);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
        return result;
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
