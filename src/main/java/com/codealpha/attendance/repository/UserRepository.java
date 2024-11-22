package com.codealpha.attendance.repository;

import com.codealpha.attendance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchUsersByKeyword(@Param("keyword") String keyword);
    
    
    boolean existsByUsername(String username);
}