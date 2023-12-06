package com.shekhar.urlshortner.Auth.Services;

import com.shekhar.urlshortner.Auth.Dto.PermissionDTO;
import com.shekhar.urlshortner.Auth.Models.Permission;
import com.shekhar.urlshortner.Auth.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission addPermission(PermissionDTO permissionDTO){
        Permission permission = new Permission();
        permission.setName(permissionDTO.getName());
        return permissionRepository.save(permission);
    }


}
