package ru.duckcoder.fintrack.backend.controller.api.v1;

import ru.duckcoder.fintrack.backend.controller.Controllable;

public interface APIv1 extends Controllable {
    @Override
    default String apiPath() {
        return "/api/v1";
    }
}
