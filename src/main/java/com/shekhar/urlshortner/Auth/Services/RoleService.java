package com.shekhar.urlshortner.Auth.Services;

import com.shekhar.urlshortner.Auth.Dto.RoleDTO;
import com.shekhar.urlshortner.Auth.Models.Role;
import com.shekhar.urlshortner.Auth.Models.Permission;
import com.shekhar.urlshortner.Auth.Repository.PermissionRepository;
import com.shekhar.urlshortner.Auth.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public void addRole(RoleDTO roleDTO){
        Role role = new Role();
        role.setName(roleDTO.getName());
        List<Permission> permissionsList = permissionRepository.findByNameIn(roleDTO.getPermissions());
        Set<Permission> permissionSet = new HashSet<>(permissionsList);
        role.setPermissions(permissionSet);
        roleRepository.save(role);
    }
}
