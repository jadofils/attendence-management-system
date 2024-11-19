package com.codealpha.attendance.service.userservice;

import java.util.List;

import com.codealpha.attendance.model.User;

public interface UserService {
 
User saveUser(User user);

List<User>getAllUsers();

}
