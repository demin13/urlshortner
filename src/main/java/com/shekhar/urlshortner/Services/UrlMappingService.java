package com.shekhar.urlshortner.Services;

import com.shekhar.urlshortner.Entities.UrlMapping;
import com.shekhar.urlshortner.Repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlMappingService(UrlMappingRepository urlMappingRepository){
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping shortenUrl(String originalUrl, LocalDate expiryDate){
        String shortenedUrl = generateShortenedUrl();
        UrlMapping urlMapping = new UrlMapping(originalUrl, shortenedUrl, LocalDateTime.now(), expiryDate);
        return urlMappingRepository.save(urlMapping);
    }

    public Optional<UrlMapping> findByOriginalUrl(String originalUrl){
        return urlMappingRepository.findByOriginalUrl(originalUrl);
    }

    public Optional<UrlMapping> findByShortenedUrl(String shortenedUrl){
        return urlMappingRepository.findByShortenedUrl(shortenedUrl);
    }

    public List<UrlMapping> findByExpiryDate(LocalDate date){
        return urlMappingRepository.findByExpiryDate(date);
    }

    private String generateShortenedUrl() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = new byte[16];
        ByteBuffer.wrap(bytes).putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());
        String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return "http://localhost:8080/" + base64.substring(0, 9);
    }


}
