package com.shekhar.urlshortner.Auth.Controller;

import com.shekhar.urlshortner.Auth.Dto.PermissionDTO;
import com.shekhar.urlshortner.Auth.Permission;
import com.shekhar.urlshortner.Auth.Services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shekhar.urlshortner.Auth.Enums.LogicsEnum;
import com.shekhar.urlshortner.Auth.Enums.PermissionsEnum;



@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @PostMapping("/add/")
//    @Permission(permissions = {PermissionsEnum.ALLOWREAD, PermissionsEnum.ALLOWWRITE, PermissionsEnum.ALLOWUPDATE},
//            type = LogicsEnum.ANY)
    public ResponseEntity<String> createPermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.addPermission(permissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Permission Added Successfully");
    }

}
