package com.dkkhoa.possystem.model.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUserId(Integer user_id);
    User findByUsername(String name);

    long countByIsAdminFalse();
}
