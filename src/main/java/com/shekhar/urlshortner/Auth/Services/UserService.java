package com.shekhar.urlshortner.Auth.Services;

import com.shekhar.urlshortner.Auth.Dto.UserDTO;
import com.shekhar.urlshortner.Auth.Models.Role;
import com.shekhar.urlshortner.Auth.Models.User;
import com.shekhar.urlshortner.Auth.Repository.RoleRepository;
import com.shekhar.urlshortner.Auth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(encodePassword(userDTO.getPassword()));
        Set<Role> userRoles = findRolesByNames(userDTO.getRoles());
        newUser.setRoles(userRoles);
        return userRepository.save(newUser);
    }

    private Set<Role> findRolesByNames(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(role);
        }
        return roles;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
