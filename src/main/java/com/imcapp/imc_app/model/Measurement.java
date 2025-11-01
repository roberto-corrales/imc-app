package com.imcapp.imc_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relación N:1 con User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @DecimalMin("0.01")
    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @DecimalMin("1.00") @DecimalMax("2.50")
    @Column(name = "estatura_m_snapshot", nullable = false)
    private Double estaturaSnapshot;

    @DecimalMin("5.00") @DecimalMax("80.00") // rango razonable
    @Column(name = "imc", nullable = false)
    private Double imc;

    @Column(name = "tomado_en_utc", nullable = false)
    private LocalDateTime tomadoEnUtc;

    @PrePersist
    void prePersist() {
        if (tomadoEnUtc == null) {
            tomadoEnUtc = LocalDateTime.now();
        }
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public Double getEstaturaSnapshot() { return estaturaSnapshot; }
    public void setEstaturaSnapshot(Double estaturaSnapshot) { this.estaturaSnapshot = estaturaSnapshot; }
    public Double getImc() { return imc; }
    public void setImc(Double imc) { this.imc = imc; }
    public LocalDateTime getTomadoEnUtc() { return tomadoEnUtc; }
    public void setTomadoEnUtc(LocalDateTime tomadoEnUtc) { this.tomadoEnUtc = tomadoEnUtc; }
}
