package br.ufrn.tads.web_car_store.controller;

import br.ufrn.tads.web_car_store.domain.Usuario;
import br.ufrn.tads.web_car_store.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/cadusuario")
    public String getCadUsuarioPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "form-usuario";
    }

    @PostMapping("/salvarusuario")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        service.save(usuario);
        return "redirect:/login";
    }
}