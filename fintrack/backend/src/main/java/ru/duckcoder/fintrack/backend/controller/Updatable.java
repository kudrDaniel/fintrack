package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;

@FunctionalInterface
public interface Updatable {
    void update(Context ctx);
}
