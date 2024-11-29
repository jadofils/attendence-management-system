package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;

public interface LoginService {

    // Method to authenticate user based on username, password, and role
    String authenticate(String username, String password, String role);

    // Method to fetch user role based on username
    UserRole getUserRole(String username);

    // Method to set user in the session
    void setUserInSession(User user);

    User getUserDetails(String username);
}
