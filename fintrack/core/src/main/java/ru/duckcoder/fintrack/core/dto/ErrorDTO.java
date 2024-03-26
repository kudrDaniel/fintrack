package ru.duckcoder.fintrack.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorDTO {
    private final String message;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ErrorDTO(
            @JsonProperty(value = "message", required = true) String message
    ) {
        this.message = message;
    }
}
