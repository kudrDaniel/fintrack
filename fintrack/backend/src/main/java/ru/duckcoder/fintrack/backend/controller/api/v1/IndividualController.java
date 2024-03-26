package ru.duckcoder.fintrack.backend.controller.api.v1;

public interface IndividualController extends PersonController {
    @Override
    default String rootPath() {
        return new StringBuilder()
                .append(PersonController.super.rootPath())
                .append('/').append("individuals")
                .toString();
    }
}
