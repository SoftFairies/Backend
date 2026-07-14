package com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest.dto;

import lombok.Data;

@Data
public class CuriosityResponse {
    private Long id;
    private String title;
    private String fact;
    private String genre;
    private String bookReference;
}