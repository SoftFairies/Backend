package com.fairies.api.proyecto.common.domain.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface StorageService {
    StorageResponse upload(MultipartFile file) throws IOException;
    void delete(String publicId) throws IOException;
}