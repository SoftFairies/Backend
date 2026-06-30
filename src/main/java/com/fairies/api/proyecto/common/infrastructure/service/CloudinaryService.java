package com.fairies.api.proyecto.common.infrastructure.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService implements StorageService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Override
    public StorageResponse upload(MultipartFile multipartFile) throws IOException {
        Map<?, ?> options = ObjectUtils.asMap(
                "folder", "lm_upload"
        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
        return new StorageResponse(
                (String) uploadResult.get("url"),
                (String) uploadResult.get("public_id")
        );
    }

    @Override
    public void delete(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}