package ru.duckcoder.fintrack.backend.mapper;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.user.UserCreateDTO;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.user.UserUpdateDTO;
import ru.duckcoder.fintrack.backend.model.user.User;

@Log4j2
public class UserMapper extends AbstractMapper<
        UserDTO,
        UserCreateDTO,
        UserUpdateDTO,
        User> {
    public UserMapper(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User map(UserCreateDTO dto) {
        User model = new User();
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        return model;
    }

    @Override
    public UserDTO map(User model) {
        return new UserDTO(model.getId(), model.getUsername());
    }

    @Override
    public void update(UserUpdateDTO dto, User model) {
        if (dto.getUsername() != null) {
            String newUsername;
            if (dto.getUsername().isPresent())
                newUsername = dto.getUsername().get();
            else {
                log.warn("Username can not be null");
                newUsername = model.getUsername();
            }
            model.setUsername(newUsername);
        } else
            log.debug("Username unchanged");
        if (dto.getPassword() != null) {
            String newPassword;
            if (dto.getPassword().isPresent())
                newPassword = dto.getPassword().get();
            else {
                log.warn("Password can not be null.");
                newPassword = model.getPassword();
            }
            model.setPassword(newPassword);
        } else
            log.debug("Password unchanged");
    }
}
