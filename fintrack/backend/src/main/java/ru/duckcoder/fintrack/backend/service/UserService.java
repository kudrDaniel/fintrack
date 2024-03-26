package ru.duckcoder.fintrack.backend.service;

import ru.duckcoder.fintrack.core.dto.user.UserCreateDTO;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.user.UserUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

import java.util.List;

public interface UserService extends AbstractService<UserDTO, UserCreateDTO, UserUpdateDTO, Long> {
    List<PersonDTO> readAllPersons(Long id);
}
