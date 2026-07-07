package com.fairies.api.proyecto.modules.book.infrastructure.client.dto;

import java.util.List;

public record GoogleBooksResponse(List<Item> items) {
    public record Item(VolumeInfo volumeInfo) {}
    public record VolumeInfo(
            String title,
            List<String> authors,
            List<String> categories,
            Integer pageCount,
            String description,
            ImageLinks imageLinks,
            List<IndustryIdentifier> industryIdentifiers
    ) {}
    public record ImageLinks(String thumbnail) {}
    public record IndustryIdentifier(String type, String identifier) {}
}