package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T> {
    private final List<T> entities = new ArrayList<>();

    public List<T> findAll(Predicate<T> predicate) {
        return entities.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void save(T entity) {
        UUID nextId = generateId();
        setId(entity, nextId);
        entities.add(entity);
    }

    protected abstract void setId(T entity, UUID id);

    private UUID generateId() {
        return UUID.randomUUID();
    }
}
