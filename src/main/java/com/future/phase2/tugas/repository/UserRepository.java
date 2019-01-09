package com.future.phase2.tugas.repository;

import com.future.phase2.tugas.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    Users findByUsername(String username);
}
