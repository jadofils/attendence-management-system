package com.codealpha.attendance.service.userservice;

import java.util.List;

import com.codealpha.attendance.dto.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId, UserDTO updatedUserDTO);
    void deleteUser(Long userId);
    long countUsers();
    List<UserDTO> searchUsersByUsername(String username);
    List<UserDTO> searchUsersByRole(String role);
}
