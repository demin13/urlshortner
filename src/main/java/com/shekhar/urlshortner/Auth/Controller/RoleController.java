package com.shekhar.urlshortner.Auth.Controller;

import com.shekhar.urlshortner.Auth.Dto.RoleDTO;
import com.shekhar.urlshortner.Auth.Models.Role;
import com.shekhar.urlshortner.Auth.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add/")
    public ResponseEntity<String> createRole(@RequestBody RoleDTO roleDto){
        roleService.addRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role added successfully");
    }

}
