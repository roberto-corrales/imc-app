package com.imcapp.imc_app.service;

import com.imcapp.imc_app.model.Measurement;
import com.imcapp.imc_app.model.User;
import com.imcapp.imc_app.repository.MeasurementRepository;
import com.imcapp.imc_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeasurementService {

    private final MeasurementRepository measurements;
    private final UserRepository users;
    private final BMIService bmi;
    private final Clock clock;

    public MeasurementService(MeasurementRepository measurements, UserRepository users, BMIService bmi, Clock clock) {
        this.measurements = measurements;
        this.users = users;
        this.bmi = bmi;
        this.clock = clock;
    }

    // Registra una nueva medición para el usuario (usa la estatura actual del usuario)
    public Measurement registrarMedicion(Long userId, Double pesoKg) {
        if (pesoKg == null || pesoKg <= 0) throw new IllegalArgumentException("El peso debe ser mayor a 0.");
        User u = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        double imc = bmi.calcular(pesoKg, u.getEstatura());

        Measurement m = new Measurement();
        m.setUser(u);
        m.setPesoKg(pesoKg);
        m.setEstaturaSnapshot(u.getEstatura());
        m.setImc(imc);
        m.setTomadoEnUtc(LocalDateTime.now(clock));

        return measurements.save(m);
    }

    public List<Measurement> historial(Long userId) {
        return measurements.findAllByUserIdOrderByTomadoEnUtcDesc(userId);
    }

    public Optional<Measurement> ultima(Long userId) {
        return measurements.findTop1ByUserIdOrderByTomadoEnUtcDesc(userId);
    }

    public void eliminar(Long measurementId) {
        measurements.deleteById(measurementId);
    }
}
