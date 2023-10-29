package com.shekhar.urlshortner.littleurl.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenDTO {

    private String originalUrl;
    private LocalDate expiryDate;

}
