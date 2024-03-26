package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;

@FunctionalInterface
public interface Deleted {
    void delete(Context ctx);
}
