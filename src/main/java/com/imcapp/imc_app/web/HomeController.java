package com.imcapp.imc_app.web;

import com.imcapp.imc_app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService users;

    public HomeController(UserService users) {
        this.users = users;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null) return "redirect:/login";
        var u = users.obtenerPorId(uid).orElseThrow();
        model.addAttribute("nombre", u.getNombreCompleto());
        return "home"; // templates/home.html
    }
}
