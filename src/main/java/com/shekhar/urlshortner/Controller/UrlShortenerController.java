package com.shekhar.urlshortner.Controller;


import com.shekhar.urlshortner.Entities.UrlMapping;
import com.shekhar.urlshortner.Services.UrlMappingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/url")
public class UrlShortenerController {
    private final UrlMappingService urlMappingService;

    @Autowired
    public UrlShortenerController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping("/shorten/")
    public UrlMapping shortenUrl(@RequestBody UrlShortenRequest request) throws RuntimeException{
        return urlMappingService.shortenUrl(request.getOriginalUrl(), request.getExpiryDate());
    }

    @GetMapping("/original/")
    public Optional<UrlMapping> findByOriginalUrl(@RequestParam("originalUrl") String originalUrl) {
        return urlMappingService.findByOriginalUrl(originalUrl);
    }

    @GetMapping("/shorten/")
    public Optional<UrlMapping> findByShortenedUrl(@RequestParam("shortenUrl") String shortenUrl) {
        return urlMappingService.findByShortenedUrl(shortenUrl);
    }

    @GetMapping("/listall/")
    public List<UrlMapping> findByExpiryDate(@RequestParam("expiry_date") LocalDate expiry_date) {
        return urlMappingService.findByExpiryDate(expiry_date);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
        System.out.print("hi error0");
        return new ErrorInfo(req.getRequestURL().toString(), ex.getLocalizedMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    class ErrorInfo {

        public final String url;
        public final String ex;

        public final String responsecode;

        public ErrorInfo(String url, String ex, String responsecode) {
            this.url = url;
            this.ex = ex;
            this.responsecode = responsecode;
        }
    }

}
