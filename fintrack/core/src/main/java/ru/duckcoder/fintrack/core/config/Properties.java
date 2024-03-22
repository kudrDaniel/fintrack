package ru.duckcoder.fintrack.core.config;

import lombok.Getter;

@Getter
public enum Properties {
    FINTRACK_SERVER_DIR("commons//backend"),
    FINTRACK_SERVER_PORT("8080");

    private final String def;

    Properties(String def) {
        this.def = def;
    }

    public String getKey() {
        return this.name();
    }
}
