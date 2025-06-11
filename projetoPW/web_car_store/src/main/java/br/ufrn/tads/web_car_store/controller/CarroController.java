package br.ufrn.tads.web_car_store.controller;

import br.ufrn.tads.web_car_store.domain.Carro;
import br.ufrn.tads.web_car_store.service.CarroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // <-- A LINHA QUE FALTAVA

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarroController {

    private final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @ModelAttribute("cartCount")
    public int getCartCount(HttpSession session) {
        List<Carro> carrinho = (List<Carro>) session.getAttribute("carrinho");
        if (carrinho != null) {
            return carrinho.size();
        }
        return 0;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        List<Carro> carros = service.findAllNonDeleted();
        model.addAttribute("carros", carros);

        if(service.findAll().isEmpty()){

        };

        return "index";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<Carro> carros = service.findAll();
        model.addAttribute("carros", carros);
        return "admin";
    }

    @GetMapping("/cadastro")
    public String getCadastroPage(Model model) {
        model.addAttribute("carro", new Carro());
        return "form-cadastro";
    }

    @GetMapping("/editar/{id}")
    public String getEditarPage(@PathVariable Long id, Model model) {
        Optional<Carro> optionalCarro = service.findById(id);
        if (optionalCarro.isPresent()) {
            model.addAttribute("carro", optionalCarro.get());
            return "form-cadastro";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/salvar")
    public String doSalvar(@ModelAttribute @Valid Carro carro, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form-cadastro";
        }
        service.save(carro);
        return "redirect:/index";
    }

    @GetMapping("/deletar/{id}")
    public String deletarPage(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/restaurar/{id}")
    public String restaurarPage(@PathVariable Long id) {
        service.restore(id);
        return "redirect:/admin";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Carro> carrinho = (List<Carro>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }
        Optional<Carro> optionalCarro = service.findById(id);
        optionalCarro.ifPresent(carrinho::add);
        session.setAttribute("carrinho", carrinho);
        return "redirect:/index";
    }

    @GetMapping("/verCarrinho")
    public String getCarrinhoPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        List<Carro> carrinho = (List<Carro>) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "Seu carrinho está vazio!");
            return "redirect:/index";
        }
        double total = carrinho.stream().mapToDouble(Carro::getPreco).sum();
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", total);
        return "ver-carrinho";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session) {
        // Invalida a sessão atual, removendo todos os atributos (incluindo o carrinho)
        session.invalidate(); //

        // Redireciona o usuário para a página inicial
        return "redirect:/index"; //
    }
}