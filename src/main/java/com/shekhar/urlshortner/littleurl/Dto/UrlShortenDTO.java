package com.shekhar.urlshortner.littleurl.Dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenDTO implements Serializable {
    private String originalUrl;
    private LocalDate expiryDate;

}
