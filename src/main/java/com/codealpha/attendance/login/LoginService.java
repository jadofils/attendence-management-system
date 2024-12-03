package com.codealpha.attendance.login;


import com.codealpha.attendance.model.User;

public interface LoginService {
    User authenticate(String username, String password, String role);
}
