package com.shekhar.urlshortner.Repository;

import com.shekhar.urlshortner.Entities.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);

    Optional<UrlMapping> findByShortenedUrl(String shortenedUrl);
    List<UrlMapping> findByExpiryDate(LocalDate date);
    void deleteByExpiryDateBefore(LocalDate date);
}
