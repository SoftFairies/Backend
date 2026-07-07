package com.fairies.api.proyecto.modules.readingsession.aplication;


import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence.ReadingSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class AddReadingSessionUseCase {
    private final ReadingSessionRepository repository;
    private final UserRepository userRepository;

    public AddReadingSessionUseCase(ReadingSessionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public ReadingSession execute(ReadingSession session) {
        if (session == null) {
            throw new IllegalArgumentException("La sesión de lectura no puede ser nula.");
        }
        if (session.getUser() == null || session.getUser().getId() == null) {
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

        //Buscamos y asignamos el usuario completo
        var realUser = userRepository.findById(session.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe."));
        session.setUser(realUser);

        repository.findBookIdNative(session.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("El libro especificado no existe."));

        return repository.save(session);
    }
}