package com.codealpha.attendance.service.userservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.repository.UserRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
      return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
      return userRepository.findAll();
    }

}
