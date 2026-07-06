package com.fairies.api.proyecto.modules.readingsession.aplication;

import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import org.springframework.stereotype.Component;

@Component
public class AddReadingSessionUseCase {

    public ReadingSession execute(ReadingSession session) {
        if (session == null) {
            throw new IllegalArgumentException("La sesión de lectura no puede ser nula.");
        }
        if (session.getUsuarioId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        if (session.getBook() == null || session.getBook().getId() == null) {
            throw new IllegalArgumentException("El libro es obligatorio.");
        }
        if (session.getSegundosLeidos() == null || session.getSegundosLeidos() <= 0) {
            throw new IllegalArgumentException("Los segundos leídos deben ser mayores a 0.");
        }
        if (session.getPaginasAvanzadas() == null || session.getPaginasAvanzadas() < 0) {
            throw new IllegalArgumentException("Las páginas avanzadas no pueden ser negativas.");
        }
        return session;
    }
}