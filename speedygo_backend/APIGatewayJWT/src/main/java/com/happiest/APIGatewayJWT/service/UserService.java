package com.happiest.APIGatewayJWT.service;


import com.happiest.APIGatewayJWT.LoginResponse;
import com.happiest.APIGatewayJWT.model.Users;
import com.happiest.APIGatewayJWT.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        return repo.save(user);
    }

    public LoginResponse verify(Users user) {
        // Authenticate the user
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            // Generate JWT token
            String token = jwtService.generateToken(user.getEmail());

            // Fetch user details from the repository using email
            Users loggedInUser = repo.findByEmail(user.getEmail());

            // Create and return the LoginResponse object
            return new LoginResponse(
                    token,
                    loggedInUser.getEmail(),
                    loggedInUser.getUsername(),
                    loggedInUser.getRoles().name(),
                    loggedInUser.getPhonenumber()
            );
        } else {
            return null;
        }
    }

    @Transactional
    public String forgotPassword(String email) {
        Users user = repo.findByEmail(email);
        if (user != null) {
            return "User exists.";
        } else {
            return "User not found.";
        }
    }

    @Transactional
    public String updatePassword(String email, String newPassword) {
        Users user = repo.findByEmail(email);
        if (user != null) {
            // Check if the new password matches the current password
            if (encoder.matches(newPassword, user.getPassword())) {
                return "New password cannot be the same as the current password.";
            }
            // Encode the new password and save it
            String encodedNewPassword = encoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            repo.save(user);
            return "Password reset successful.";
        } else {
            return "User not found.";
        }
    }



    public String isEmailPresent(String email) {
        Users user = repo.findByEmail(email);
        if (user != null) {
            return "Email present";
        }
        else
            return "Email not found";
    }


}



