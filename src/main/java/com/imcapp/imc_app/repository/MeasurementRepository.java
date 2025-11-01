package com.imcapp.imc_app.repository;

import com.imcapp.imc_app.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAllByUserIdOrderByTomadoEnUtcDesc(Long userId);
    Optional<Measurement> findTop1ByUserIdOrderByTomadoEnUtcDesc(Long userId);
    }
