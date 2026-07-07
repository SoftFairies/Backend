package com.fairies.api.proyecto.modules.recommendation.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import com.fairies.api.proyecto.modules.recommendation.application.*;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.dto.*;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.mapper.RecommendationMapper;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/preferences")
@RequiredArgsConstructor
public class RecommendationRouting {
    private final SavePreferenceUseCase saveUseCase;
    private final GetRecommendationUseCase recUseCase;
    private final GetPreferenceUseCase getPrefUseCase;
    private final JwtService jwtService;
    private final UserRepository userRepo;
    private final FormatRepository formatRepo;
    private final GenderRepository genderRepo;
    private final BookMapper bookMapper;
    private final RecommendationMapper recMapper;

    @PostMapping
    public void create(@RequestHeader("Authorization") String auth, @RequestBody RecommendationRequest req) {
        handleSave(auth, req);
    }

    @PutMapping
    public void update(@RequestHeader("Authorization") String auth, @RequestBody RecommendationRequest req) {
        handleSave(auth, req);
    }

    @GetMapping
    public ResponseEntity<RecommendationResponse> getMyPreferences(@RequestHeader("Authorization") String auth) {
        var userId = jwtService.getUserIdFromToken(auth);
        var pref = getPrefUseCase.execute(userId);
        return ResponseEntity.ok(recMapper.toResponse(pref));
    }

    @GetMapping("/recommendations")
    public List<BookResponse> getRecommendations(@RequestHeader("Authorization") String auth) {
        var userId = jwtService.getUserIdFromToken(auth);
        return recUseCase.execute(userId).stream().map(bookMapper::toResponse).toList();
    }

    private void handleSave(String auth, RecommendationRequest request) {
        var userId = jwtService.getUserIdFromToken(auth);
        var user = userRepo.findById(userId).orElseThrow();
        var formats = request.formatIds().stream().map(id -> formatRepo.findById(id).orElseThrow()).collect(Collectors.toSet());
        var genres = request.genreIds().stream().map(id -> genderRepo.findById(id).orElseThrow()).collect(Collectors.toSet());
        saveUseCase.execute(user, formats, genres);
    }
}