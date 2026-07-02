package com.fairies.api.proyecto.modules.mailbox.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.mailbox.application.GetReceivedLettersUseCase;
import com.fairies.api.proyecto.modules.mailbox.application.SendLetterUseCase;
import com.fairies.api.proyecto.modules.mailbox.domain.model.Letter;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto.LetterResponse;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto.SendLetterRequest;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.mapper.LetterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mailbox")
@RequiredArgsConstructor
public class MailboxRouting {

    private final SendLetterUseCase sendLetterUseCase;
    private final GetReceivedLettersUseCase getReceivedLettersUseCase;
    private final LetterMapper letterMapper;
    private final JwtService jwtService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendLetter(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody SendLetterRequest request
    ) {
        UUID authenticatedSenderId = jwtService.getUserIdFromToken(authHeader);
        sendLetterUseCase.execute(authenticatedSenderId, request.bookId(), request.content());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/received")
    public ResponseEntity<List<LetterResponse>> getMyLetters(
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID authenticatedReceiverId = jwtService.getUserIdFromToken(authHeader);
        List<Letter> letters = getReceivedLettersUseCase.execute(authenticatedReceiverId);
        return ResponseEntity.ok(letterMapper.toResponseList(letters));
    }
}