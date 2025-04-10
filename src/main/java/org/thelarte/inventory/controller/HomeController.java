package org.thelarte.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome() {
        return "index";  // Busca el archivo index.html en src/main/resources/templates
    }

    @GetMapping("/editar")
    public String vistaEditar() {
        return "editar";  // templates/editar.html
    }
}
