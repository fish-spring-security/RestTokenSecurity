package com.github.fish56.resttokensecurity.repository;

import com.github.fish56.resttokensecurity.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByToken(String token);
}
