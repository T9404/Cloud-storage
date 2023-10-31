package org.example.repository.impl;

import org.example.entity.User;
import org.example.repository.AbstractRepository;

import java.util.UUID;

public class UserRepository extends AbstractRepository<User> {

    @Override
    protected void setId(User entity, UUID id) {
        entity.setId(id);
    }
}
