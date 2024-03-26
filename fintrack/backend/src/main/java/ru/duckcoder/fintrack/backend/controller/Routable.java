package ru.duckcoder.fintrack.backend.controller;

import io.javalin.Javalin;

public interface Routable {
    void route(Javalin javalin);

    String apiPath();
}
