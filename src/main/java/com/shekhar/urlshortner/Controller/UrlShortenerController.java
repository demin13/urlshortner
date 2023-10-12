package com.shekhar.urlshortner.Controller;


import com.shekhar.urlshortner.Entities.UrlMapping;
import com.shekhar.urlshortner.Services.UrlMappingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("")
public class UrlShortenerController {
    private final UrlMappingService urlMappingService;

    @Autowired
    public UrlShortenerController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping("api/v1/url/shorten/")
    public ResponseEntity<Object> shortenUrl(@RequestBody UrlShortenRequest request) throws RuntimeException {
        Map<String, Object> responseData = new HashMap<>();
        try {
            UrlMapping urlMapping = urlMappingService.shortenUrl(request.getOriginalUrl(), request.getExpiryDate());
            responseData.put("shortenedUrl", urlMapping.getShortenedUrl());
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }
        catch (Exception e){
            responseData.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @GetMapping("api/v1/url/original/")
    private ResponseEntity<Object> findByOriginalUrl(@RequestParam("originalUrl") String originalUrl) {
        Map<String, Object> responseData = new HashMap<>();
        try {
            Optional<UrlMapping> urlInstance = urlMappingService.findByOriginalUrl(originalUrl);
            if (urlInstance.isPresent()) {
                UrlMapping urlResponse = urlInstance.get();
                responseData.put("id", urlResponse.getId());
                responseData.put("original URL", urlResponse.getOriginalUrl());
                responseData.put("shorten URL", urlResponse.getShortenedUrl());
                responseData.put("Expiry Date", urlResponse.getExpiryDate());
                return ResponseEntity.status(HttpStatus.OK).body(responseData);
            } else {
                responseData.put("message", "Provided original URL does not exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
        } catch (Exception e) {
            responseData.put("message", e.getMessage());
            return ResponseEntity.status(500).body(responseData);
        }

    }

    @GetMapping("api/v1/url/shorten/")
    private ResponseEntity<Object> findByShortenedUrl(@RequestParam("shortenUrl") String shortenUrl) {
        Map<String, Object> responseData = new HashMap<>();
        try {
            Optional<UrlMapping> urlInstance = urlMappingService.findByShortenedUrl(shortenUrl);
            if (urlInstance.isPresent()) {
                UrlMapping urlResponse = urlInstance.get();
                responseData.put("id", urlResponse.getId());
                responseData.put("original URL", urlResponse.getOriginalUrl());
                responseData.put("shorten URL", urlResponse.getShortenedUrl());
                responseData.put("Expiry Date", urlResponse.getExpiryDate());
                return ResponseEntity.status(HttpStatus.OK).body(responseData);
            } else {
                responseData.put("message", "Provided shorten url either expired or does not exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
        } catch (Exception e) {
            responseData.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @GetMapping("/{shorturl}")
    public RedirectView redirectToLong(@PathVariable String shorturl) {
        try {
            String longUrl = urlMappingService.getLongUrl(shorturl);
            if (longUrl != null) {
                RedirectView redirectView = new RedirectView(longUrl);
                redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                return redirectView;
            } else {
                return new RedirectView("/error", true);
            }
        }
        catch(Exception e){
            return new RedirectView("/error", true);
        }
    }

    @GetMapping("api/v1/url/get/all")
    public List<UrlMapping> findByExpiryDate(@RequestParam("expiry_date") LocalDate expiry_date) {
        return urlMappingService.findByExpiryDate(expiry_date);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex.getLocalizedMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    class ErrorInfo {

        public final String url;
        public final String ex;
        public final String responseCode;

        public ErrorInfo(String url, String ex, String responseCode) {
            this.url = url;
            this.ex = ex;
            this.responseCode = responseCode;
        }
    }

}
