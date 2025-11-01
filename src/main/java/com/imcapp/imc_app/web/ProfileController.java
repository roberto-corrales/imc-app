package com.imcapp.imc_app.web;

import com.imcapp.imc_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

record ProfileForm(
        @NotBlank @Size(max=120) String nombreCompleto,
        @NotNull @Min(15) Integer edad,
        @NotBlank @Pattern(regexp="M|F|O") String sexo,
        @NotNull @DecimalMin("1.00") @DecimalMax("2.50") Double estatura
) {}

record PasswordForm(
        @NotBlank String actual,
        @NotBlank @Size(min=8) String nueva,
        @NotBlank String confirmar
) {}

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService users;

    public ProfileController(UserService users) {
        this.users = users;
    }

    private Long requireUid(HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null) throw new IllegalStateException("No autenticado");
        return uid;
    }

    @GetMapping
    public String profile(Model model, HttpSession session) {
        var u = users.obtenerPorId(requireUid(session)).orElseThrow();
        model.addAttribute("u", u);
        users.obtenerPorId(u.getId());
        return "user/profile"; // templates/user/profile.html
    }

    @GetMapping("/edit")
    public String editForm(Model model, HttpSession session) {
        var u = users.obtenerPorId(requireUid(session)).orElseThrow();
        model.addAttribute("form", new ProfileForm(u.getNombreCompleto(), u.getEdad(), u.getSexo(), u.getEstatura()));
        return "user/edit"; // templates/user/edit.html
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("form") ProfileForm form,
                         BindingResult result,
                         HttpSession session) {
        if (result.hasErrors()) return "user/edit";
        var uid = requireUid(session);
        users.actualizarPerfil(uid, form.nombreCompleto(), form.edad(), form.sexo(), form.estatura());
        return "redirect:/profile";
    }

    @GetMapping("/password")
    public String pwdForm(Model model) {
        model.addAttribute("form", new PasswordForm("", "", ""));
        return "user/change-password"; // templates/user/change-password.html
    }

    @PostMapping("/password")
    public String changePwd(@ModelAttribute("form") PasswordForm form,
                            BindingResult result,
                            HttpSession session) {
        if (!form.nueva().equals(form.confirmar())) {
            result.reject("pwd", "La nueva contraseña y la confirmación no coinciden");
            return "user/change-password";
        }
        var uid = requireUid(session);
        try {
            users.cambiarPassword(uid, form.actual(), form.nueva());
            return "redirect:/profile";
        } catch (IllegalArgumentException ex) {
            result.reject("pwd", ex.getMessage());
            return "user/change-password";
        }
    }
}
