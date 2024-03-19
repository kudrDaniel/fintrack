package ru.duckcoder.fintrack.dao;

@FunctionalInterface
public interface EntityDeleteById<ID> {
    void deleteById(ID id);
}
