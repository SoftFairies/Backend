package com.fairies.api.proyecto.modules.streak.application;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateProvider {

    //CAMBIA ESTO A 'false' CUANDO VAYA A PRODUCCIÓN <-- SUPER HIPER MEGA IMPORTANTE
    private static final boolean TEST_MODE = false;

    private static final long START_TIME_MS = System.currentTimeMillis();

    public LocalDate getToday() {
        if (TEST_MODE) {
            long elapsedMillis = System.currentTimeMillis() - START_TIME_MS;

            // 2 minutos = 2 * 60 * 1000 = 120,000 milisegundos
            // Dividimos el tiempo transcurrido entre 120,000 para saber cuántos "días virtuales" sumar
            long simulatedDaysPassed = elapsedMillis / 6_000;

            return LocalDate.now().plusDays(simulatedDaysPassed);
        }

        return LocalDate.now();
    }
}