package ru.duckcoder.fintrack.backend.service;
import ru.duckcoder.fintrack.core.dto.AbstractCreateDTO;
import ru.duckcoder.fintrack.core.dto.AbstractDTO;
import ru.duckcoder.fintrack.core.dto.AbstractUpdateDTO;

import java.util.List;

public interface AbstractService<
        D extends AbstractDTO,
        CD extends AbstractCreateDTO,
        UD extends AbstractUpdateDTO,
        ID> {
    D create(CD dto);
    List<D> readAll();
    D read(ID id);
    D update(ID id, UD dto);
    void delete(ID id);
}
