package ru.duckcoder.fintrack.backend.controller.api.v1;

public interface PersonController extends APIv1 {
    @Override
    default String rootPath() {
        return new StringBuilder()
                .append(this.apiPath())
                .append('/').append("persons")
                .toString();
    }
}
