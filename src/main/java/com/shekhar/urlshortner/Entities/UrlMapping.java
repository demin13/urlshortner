package com.shekhar.urlshortner.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "url_store")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortenedUrl;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column
    private LocalDate expiryDate;

    public UrlMapping(String originalUrl, String shortenedUrl, LocalDateTime creationDate, LocalDate expiryDate){
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
    }

}
