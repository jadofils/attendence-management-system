package com.codealpha.attendance.service.userservice;

import com.codealpha.attendance.model.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    User updateUser(Long userId, User updatedUser);
    void deleteUser(Long userId);
    long countUsers();
    List<User> searchUsersByUsername(String username);
    List<User> searchUsersByRole(String keyword);
    Object findById(Long userId);}