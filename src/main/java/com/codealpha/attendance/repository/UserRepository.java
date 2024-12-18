package com.codealpha.attendance.repository;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find users by username (case-insensitive)
    List<User> findByUsernameContainingIgnoreCase(String username);

    // Search users by keyword in username or role
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchUsersByKeyword(@Param("keyword") String keyword);

    // Find user by username and role
    Optional<User> findByUsernameAndRole(String username, UserRole role);

    // Check if a user with a specific username exists
    boolean existsByUsername(String username);

    // Find users by role
    List<User> findByRole(UserRole role);

    Optional<User> findByUsername(String trimmedUsername);
}
