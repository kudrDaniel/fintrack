package ru.duckcoder.fintrack.backend.controller;

import io.javalin.Javalin;

public interface Routable {
    void route(Javalin javalin);

    default String apiPath() {
        return "/api";
    }
}
