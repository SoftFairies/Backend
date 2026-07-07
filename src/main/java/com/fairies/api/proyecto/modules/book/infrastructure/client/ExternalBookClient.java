package com.fairies.api.proyecto.modules.book.infrastructure.client;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import java.util.List;

public interface ExternalBookClient {
    List<CreateBookRequest> searchExternalBooks(String query);
}