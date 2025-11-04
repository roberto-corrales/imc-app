package com.imcapp.imc_app.service;

import com.imcapp.imc_app.model.User;
import com.imcapp.imc_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder, Clock clock) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.clock = clock;
    }

    // Registro de usuario
    public User registrar(String nombreCompleto, String username, String passwordRaw,
                          Integer edad, String sexo, Double estaturaM) {
        if (users.existsByUsername(username)) throw new IllegalArgumentException("El username ya existe.");
        validar(edad, sexo, estaturaM);

        User u = new User();
        u.setNombreCompleto(nombreCompleto);
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(passwordRaw));
        u.setEdad(edad);
        u.setSexo(sexo);
        u.setEstatura(estaturaM);
       
       
        return users.save(u);
    }

    // Autenticación simple (validación manual de credenciales)
    public Optional<User> validarCredenciales(String username, String passwordRaw) {
        return users.findByUsername(username)
                .filter(u -> passwordEncoder.matches(passwordRaw, u.getPassword()));
    }

    public User actualizarPerfil(Long userId, String nombreCompleto, Integer edad, String sexo, Double estaturaM) {
        validar(edad, sexo, estaturaM);
        User u = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        u.setNombreCompleto(nombreCompleto);
        u.setEdad(edad);
        u.setSexo(sexo);
        u.setEstatura(estaturaM);
        return users.save(u);
    }

    public void cambiarPassword(Long userId, String actual, String nueva) {
        User u = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (!passwordEncoder.matches(actual, u.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual no coincide.");
        }
        u.setPassword(passwordEncoder.encode(nueva));
        users.save(u);
    }

    public Optional<User> obtenerPorId(Long id) { return users.findById(id); }
    public Optional<User> obtenerPorUsername(String username) { return users.findByUsername(username); }

    private void validar(Integer edad, String sexo, Double estaturaM) {
        if (edad == null || edad < 15) throw new IllegalArgumentException("Edad mínima: 15 años.");
        if (sexo == null || !sexo.matches("M|F|O")) throw new IllegalArgumentException("Sexo inválido (M/F/O).");
        if (estaturaM == null || estaturaM < 1.0 || estaturaM > 2.5) throw new IllegalArgumentException("Estatura fuera de rango (1.00–2.50 m).");
    }
}
