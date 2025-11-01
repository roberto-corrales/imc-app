package com.imcapp.imc_app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MeasurementForm {
    @NotNull @DecimalMin("0.01")
    private Double pesoKg;

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

   
}
