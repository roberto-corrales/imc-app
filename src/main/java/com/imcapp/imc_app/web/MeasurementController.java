package com.imcapp.imc_app.web;

import com.imcapp.imc_app.dto.MeasurementForm;
import com.imcapp.imc_app.service.MeasurementService;
import com.imcapp.imc_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurements;
    private final UserService users;

    public MeasurementController(MeasurementService measurements, UserService users) {
        this.measurements = measurements;
        this.users = users;
    }

    private Long requireUid(HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null) throw new IllegalStateException("No autenticado");
        return uid;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        Long uid = requireUid(session);
        var u = users.obtenerPorId(uid).orElseThrow();
        model.addAttribute("usuario", u);
        model.addAttribute("historial", measurements.historial(uid));
        model.addAttribute("ultima", measurements.ultima(uid).orElse(null));
        return "measurements/list"; // templates/measurements/list.html
    }

    @GetMapping("/new")
    public String showNew(Model model, HttpSession session) {
        requireUid(session);
        model.addAttribute("form", new MeasurementForm());
        return "measurements/new"; // templates/measurements/new.html
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") MeasurementForm form,
                         BindingResult result,
                         HttpSession session,
                         Model model) {
        Long uid = requireUid(session);
        if (result.hasErrors()) return "measurements/new";
        try {
            measurements.registrarMedicion(uid, form.getPesoKg());
            return "redirect:/measurements";
        } catch (IllegalArgumentException ex) {
            result.reject("peso", ex.getMessage());
            return "measurements/new";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        requireUid(session);
        measurements.eliminar(id);
        return "redirect:/measurements";
    }
}
