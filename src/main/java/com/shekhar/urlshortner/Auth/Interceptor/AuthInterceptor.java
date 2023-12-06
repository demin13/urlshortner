package com.shekhar.urlshortner.Auth.Interceptor;

import com.shekhar.urlshortner.Auth.Enums.LogicsEnum;
import com.shekhar.urlshortner.Auth.JwtUtils;
import com.shekhar.urlshortner.Auth.Models.User;
import com.shekhar.urlshortner.Auth.Permission;
import com.shekhar.urlshortner.Auth.Enums.PermissionsEnum;
import com.shekhar.urlshortner.Auth.Repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
public class AuthInterceptor implements HandlerInterceptor {

//    @Value("${jwt.secret}")
    private final String secretKey="gdVE9dwRsdTBVX3yFUYenNH0f0aVV2UTmgxewf9zcBE=";

    @Autowired
    private UserRepository userRepository;

    private Key getSigninKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private String getValidEmail(String token){
        Key signingKey = getSigninKey();

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();

        Jws<Claims> jwsClaims = parser.parseClaimsJws(token);
        return jwsClaims.getBody().getSubject();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("Inside this");

        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("JWT ")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = header.split(" ")[1];
        try {
            request.setAttribute("user", getValidEmail(token));
            //Authorization
            Method method = handlerMethod.getMethod();
            if(method.isAnnotationPresent(Permission.class)){
                Permission permission = method.getAnnotation(Permission.class);
                LogicsEnum logic = permission.type();
                PermissionsEnum[] requiredPermissions = permission.permissions();
                boolean hasPermission = checkUserPermission(getValidEmail(token), requiredPermissions, logic);
                if(!hasPermission){
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not Authorized");
                    return false;
                }
            }
        }
        catch (JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean checkUserPermission(String email, PermissionsEnum[] requiredPermissions, LogicsEnum logic){
        //Optional<User> userDetails = userRepository.findByEmail(email);
        //System.out.println(userDetails.toString());
        return false;
    }

}
