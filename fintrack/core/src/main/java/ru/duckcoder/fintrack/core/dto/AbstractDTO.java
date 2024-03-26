package ru.duckcoder.fintrack.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDTO {
    private ErrorDTO error;
}
