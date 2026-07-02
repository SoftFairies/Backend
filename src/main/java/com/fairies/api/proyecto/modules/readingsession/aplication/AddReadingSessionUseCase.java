package com.fairies.api.proyecto.modules.readingsession.aplication;

import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence.ReadingSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class AddReadingSessionUseCase {

    private final ReadingSessionRepository readingSessionRepository;

    public AddReadingSessionUseCase(ReadingSessionRepository readingSessionRepository) {
        this.readingSessionRepository = readingSessionRepository;
    }

    public ReadingSession execute(ReadingSession session) {
        if (session == null) {
            throw new IllegalArgumentException("La sesión de lectura no puede ser nula.");
        }
        if (session.getUser() == null || session.getUser().getId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        if (session.getLibroId() == null) {
            throw new IllegalArgumentException("El libro es obligatorio.");
        }
        if (session.getMinutosLeidos() == null || session.getMinutosLeidos() <= 0) {
            throw new IllegalArgumentException("Los minutos leídos deben ser mayores a 0.");
        }
        if (session.getPaginasAvanzadas() == null || session.getPaginasAvanzadas() < 0) {
            throw new IllegalArgumentException("Las páginas avanzadas no pueden ser negativas.");
        }

        //Coherencia de páginas por minuto máximo 2 páginas por minuto
        double paginasPorMinuto = (double) session.getPaginasAvanzadas() / session.getMinutosLeidos();
        if (paginasPorMinuto > 2.0) {
            throw new IllegalArgumentException(
                    String.format("Velocidad de lectura no realista. No es posible leer %d páginas en %d minutos.",
                            session.getPaginasAvanzadas(), session.getMinutosLeidos())
            );
        }

        return readingSessionRepository.save(session);
    }
}