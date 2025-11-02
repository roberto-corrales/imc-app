package com.imcapp.imc_app.service;

import org.springframework.stereotype.Service;



@Service
public class BMIService {

    public double calcular(double pesoKg, double estaturaM) {
        if (pesoKg <= 0) throw new IllegalArgumentException("El peso debe ser mayor a 0.");
        if (estaturaM < 1.0 || estaturaM > 2.5) throw new IllegalArgumentException("Estatura fuera de rango (1.00–2.50 m).");
        return round2(pesoKg / (estaturaM * estaturaM));
    }

    public String clasificar(double imc) {
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25.0) return "Normal";
        if (imc < 30.0) return "Sobrepeso";
        return "Obesidad";
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

}
