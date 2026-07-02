package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest;


import com.fairies.api.proyecto.modules.readingsession.aplication.AddReadingSessionUseCase;
import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionResponse;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.mapper.ReadingSessionMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reading-sessions")
@CrossOrigin(origins = "*") // Permite que PWA se conecte sin problemas de CORS
public class ReadingSessionRouting {

    private final AddReadingSessionUseCase addUseCase;
    private final ReadingSessionMapper mapper;

    public ReadingSessionRouting(AddReadingSessionUseCase addUseCase, ReadingSessionMapper mapper) {
        this.addUseCase = addUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Transactional
    //@PreAuthorize("isAuthenticated()") // Cambia el rol según los requisitos de tu negocio
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new reading session")
    public ReadingSessionResponse create(@Valid @RequestBody ReadingSessionResponse request) {
        ReadingSession domain = mapper.toDomain(request);
        ReadingSession saved = addUseCase.execute(domain);
        return mapper.toResponse(saved);
    }
}