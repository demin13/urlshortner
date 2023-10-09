package com.shekhar.urlshortner.Controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UrlShortenRequest {

    private String originalUrl;
    private LocalDate expiryDate;

}
