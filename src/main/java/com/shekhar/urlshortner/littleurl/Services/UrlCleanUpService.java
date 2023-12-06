package com.shekhar.urlshortner.littleurl.Services;

import com.shekhar.urlshortner.littleurl.Repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UrlCleanUpService {
    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlCleanUpService(UrlMappingRepository urlMappingRepository){
        this.urlMappingRepository = urlMappingRepository;
    }

    @Scheduled(cron = "0 0 * * * ?")
    @Transactional
    public void cleanupExpiredUrls(){
        LocalDate expiryDate = LocalDate.now();
        try{
            urlMappingRepository.deleteByExpiryDateBefore(expiryDate);
        }
        catch(Exception e){
            System.out.println("Error"+e);
        }
    }
}
