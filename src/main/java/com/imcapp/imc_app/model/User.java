package com.imcapp.imc_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 120)
    @Column(name = "nombre_completo", nullable = false, length = 120)
    private String nombreCompleto;

    @NotBlank @Size(min = 4, max = 60)
    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String username;

    @NotBlank @Size(min = 8, max = 255)
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Min(15) @Max(150)
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank
    @Pattern(regexp = "M|F|O") // M/F/O
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @DecimalMin("1.00") @DecimalMax("2.50")
    @Column(name = "estatura_m", nullable = false)
    private Double estatura;

    @Column(name = "creado_en_utc", nullable = false)
    private LocalDateTime creadoEnUtc;

    @Column(name = "actualizado_en_utc", nullable = false)
    private LocalDateTime actualizadoEnUtc;

    // Relación 1:N con Measurement
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("tomadoEnUtc DESC")
    private List<Measurement> measurements = new ArrayList<>();

    @PrePersist
    void prePersist() {
        var now = LocalDateTime.now();
        this.creadoEnUtc = now;
        this.actualizadoEnUtc = now;
    }

    @PreUpdate
    void preUpdate() {
        this.actualizadoEnUtc = LocalDateTime.now();
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public Double getEstatura() { return estatura; }
    public void setEstatura(Double estatura) { this.estatura = estatura; }
    public LocalDateTime getCreadoEnUtc() { return creadoEnUtc; }
    public LocalDateTime getActualizadoEnUtc() { return actualizadoEnUtc; }
    public List<Measurement> getMeasurements() { return measurements; }
   
}
