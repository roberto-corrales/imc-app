package com.imcapp.imc_app.web;

import com.imcapp.imc_app.dto.LoginForm;
import com.imcapp.imc_app.dto.UserForm;
import com.imcapp.imc_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService users;

    public AuthController(UserService users) {
        this.users = users;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new UserForm());
        }
        return "auth/register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") UserForm form,
                        BindingResult result,
                        Model model) {
        // Errores de validación por campo (se muestran con th:errors)
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "auth/register";
        }

        // Validación de contraseñas iguales (mensaje global simple)
        if (!form.getPassword().equals(form.getConfirmarPassword())) {
            model.addAttribute("form", form);
            model.addAttribute("registerError", "Las contraseñas no coinciden");
            return "auth/register";
        }

        try {
            users.registrar(
                    form.getNombreCompleto(),
                    form.getUsername(),
                    form.getPassword(),
                    form.getEdad(),
                    form.getSexo(),
                    form.getEstatura()
            );
            // opcional: model.addAttribute("msg", "Registro exitoso. Inicia sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            // Ej: "El username ya existe." o validaciones del servicio
            model.addAttribute("form", form);
            model.addAttribute("registerError", ex.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new LoginForm());
        }
        return "auth/login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("form") LoginForm form,
                        BindingResult result,
                        HttpSession session,
                        Model model) {
        // si hay errores de validación de campos, regresa la vista
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "auth/login";
        }

        var uOpt = users.validarCredenciales(form.getUsername(), form.getPassword());
        if (uOpt.isEmpty()) {
            model.addAttribute("form", form);
            model.addAttribute("loginError", "Invalid credentials"); // <-- MENSAJE SIMPLE
            return "auth/login";                                     // <-- MISMA VISTA
        }

        session.setAttribute("uid", uOpt.get().getId());
        return "redirect:/";
    }



    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
