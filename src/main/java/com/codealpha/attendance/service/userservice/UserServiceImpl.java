package com.codealpha.attendance.service.userservice;

import com.codealpha.attendance.dto.UserDTO;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.repository.UserRepository;

import lombok.AllArgsConstructor;

import com.codealpha.attendance.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = dtoToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return entityToDto(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::entityToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

   

    @Override
    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUserDTO.getUsername());
        user.setRole(updatedUserDTO.getRole());
        user.setStudentProfile(updatedUserDTO.getStudentProfile());

        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isEmpty()) {
            user.setPassword(updatedUserDTO.getPassword());
        }

        User updatedUser = userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public List<UserDTO> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> searchUsersByRole(String role) {
        return userRepository.findByRole(UserRole.valueOf(role.toUpperCase())).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private User dtoToEntity(UserDTO userDTO) {
        return User.builder()
                .userId(userDTO.getUserId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .studentProfile(userDTO.getStudentProfile())
                .build();
    }

    private UserDTO entityToDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .studentProfile(user.getStudentProfile())
                .build();
    }
}
