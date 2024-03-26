package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;

public interface Controllable extends Created, Deleted, Readable, Updatable, Routable {
    void readAll(Context ctx);
    String rootPath();
}
