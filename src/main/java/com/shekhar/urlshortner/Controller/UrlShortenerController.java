package com.shekhar.urlshortner.Controller;


import com.shekhar.urlshortner.Entities.UrlMapping;
import com.shekhar.urlshortner.Services.UrlMappingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public UrlMapping shortenUrl(@RequestBody UrlShortenRequest request) throws RuntimeException{
        return urlMappingService.shortenUrl(request.getOriginalUrl(), request.getExpiryDate());
    }

    @GetMapping("api/v1/url/original/")
    public Optional<UrlMapping> findByOriginalUrl(@RequestParam("originalUrl") String originalUrl) {
        return urlMappingService.findByOriginalUrl(originalUrl);
    }

    @GetMapping("api/v1/url/shorten/")
    public Optional<UrlMapping> findByShortenedUrl(@RequestParam("shortenUrl") String shortenUrl) {
        return urlMappingService.findByShortenedUrl(shortenUrl);
    }

    @GetMapping("/{shorturl}")
    public RedirectView redirectToLong(@PathVariable String shorturl){
        String longUrl = urlMappingService.getLongUrl(shorturl);
        if (longUrl != null){
            HttpStatus redirectionStatus = HttpStatus.MOVED_PERMANENTLY;
            RedirectView redirectView = new RedirectView(longUrl);
            redirectView.setStatusCode(redirectionStatus);
            return redirectView;
        }
        return new RedirectView("/error", true);
    }

    @GetMapping("api/v1/url/listall/")
    public List<UrlMapping> findByExpiryDate(@RequestParam("expiry_date") LocalDate expiry_date) {
        return urlMappingService.findByExpiryDate(expiry_date);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
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
