package ru.duckcoder.fintrack.backend.dao;

@FunctionalInterface
public interface EntityDeleteById<ID> {
    void deleteById(ID id);
}
