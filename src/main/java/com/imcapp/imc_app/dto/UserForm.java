package com.imcapp.imc_app.dto;

import jakarta.validation.constraints.*;

public class UserForm {
    @NotBlank @Size(max = 120)
    private String nombreCompleto;

    @NotBlank @Size(min = 4, max = 60)
    private String username;

    @NotBlank @Size(min = 8, max = 255)
    private String password;

    @NotBlank @Size(min = 8, max = 255)
    private String confirmarPassword;

    @NotNull @Min(15)
    private Integer edad;

    @NotBlank @Pattern(regexp = "M|F|O")
    private String sexo;

    @NotNull @DecimalMin("1.00") @DecimalMax("2.50")
    private Double estatura;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    
}
