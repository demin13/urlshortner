package com.shekhar.urlshortner.Auth.Services;

import com.shekhar.urlshortner.Auth.Dto.LoginDTO;
import com.shekhar.urlshortner.Auth.Models.User;
import com.shekhar.urlshortner.Auth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shekhar.urlshortner.Auth.JwtUtils;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String loginUser(LoginDTO loginDTO){
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            if(isValidCredentials(password, user.get().getPassword())){
                return jwtUtils.generateToken(loginDTO);
            }
        }
        return "";
    }

    private boolean isValidCredentials(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

}
