package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;

@FunctionalInterface
public interface Readable {
    void read(Context ctx);
}
