package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    // Method to find user by username
    User findByUsername(String username);
}
