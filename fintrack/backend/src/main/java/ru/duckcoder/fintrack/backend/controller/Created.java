package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;

@FunctionalInterface
public interface Created {
    void create(Context ctx);
}
