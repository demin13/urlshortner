package com.shekhar.urlshortner;

import com.shekhar.urlshortner.Auth.Interceptor.AuthInterceptor;
import com.shekhar.urlshortner.littleurl.Interceptor.CreateShortnerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CreateShortnerInterceptor())
                .addPathPatterns("api/v1/url/shorten/");
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/api/auth/login/**");
    }

}
