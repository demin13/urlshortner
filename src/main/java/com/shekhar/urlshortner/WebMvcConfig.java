package com.shekhar.urlshortner;

import com.shekhar.urlshortner.Interceptor.CreateShortnerInterceptor;
import com.shekhar.urlshortner.Interceptor.RedirectionInterceptor;
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
        registry.addInterceptor(new RedirectionInterceptor())
                .addPathPatterns("/*");
    }

}
