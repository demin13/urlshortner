package com.shekhar.urlshortner.Auth;

import com.shekhar.urlshortner.Auth.Enums.LogicsEnum;
import com.shekhar.urlshortner.Auth.Enums.PermissionsEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    PermissionsEnum[] permissions();
    LogicsEnum type();
}
