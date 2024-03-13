package ru.duckcoder.fintrack.dao.extension;

@FunctionalInterface
public interface EntityDeleteById<ID> {
    void deleteById(ID id);
}
