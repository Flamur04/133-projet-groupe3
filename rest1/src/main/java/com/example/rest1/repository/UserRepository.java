
package com.example.rest1.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.rest1.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User save(User user);;
}
