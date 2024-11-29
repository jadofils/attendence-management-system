package com.codealpha.attendance.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public String authenticate(String username, String password, String role) {
        // Fetch user from the database by username
        User user = loginRepository.findByUsername(username);
        
        // Check if the user exists
        if (user == null) {
            return "User does not exist";
        }

        // Check if the provided password matches the stored password
        if (!password.equals(user.getPassword())) {
            return "Invalid password";
        }

        // Check if the provided role matches the user's role
        if (!role.equals(user.getRole().name())) {
            return "Role mismatch";
        }

        // If all checks pass, set user in session and return success message
        setUserInSession(user);
        return "Login successful";
    }

    @Override
    public UserRole getUserRole(String username) {
        // Fetch user from the database by username
        User user = loginRepository.findByUsername(username);
        
        if (user != null) {
            return user.getRole(); // Return the user's role
        }
        return null; // User not found
    }

    // Method to set user in session
    public void setUserInSession(User user) {
        // Get the current HTTP session and set the user as an attribute
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        session.setAttribute("user", user);
    }

    @Override
    public User getUserDetails(String username) {
       return loginRepository.findByUsername(username);
    }
}
