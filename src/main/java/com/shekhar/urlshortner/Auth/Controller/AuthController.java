package com.shekhar.urlshortner.Auth.Controller;

import com.shekhar.urlshortner.Auth.Dto.LoginDTO;
import com.shekhar.urlshortner.Auth.Dto.UserDTO;
import com.shekhar.urlshortner.Auth.Models.User;
import com.shekhar.urlshortner.Auth.Services.LoginService;
import com.shekhar.urlshortner.Auth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/signup")
    public User createUser(@RequestBody UserDTO userDTO){
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        String jwtToken = loginService.loginUser(loginDTO);
        if(jwtToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

}
