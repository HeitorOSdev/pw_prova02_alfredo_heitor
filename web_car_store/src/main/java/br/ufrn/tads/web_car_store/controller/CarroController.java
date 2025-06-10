package br.ufrn.tads.web_car_store.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import br.ufrn.tads.web_car_store.domain.Carro;
import br.ufrn.tads.web_car_store.service.CarroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CarroController {

    private final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        // Pede ao serviço a lista de carros não deletados
        List<Carro> carros = service.findAllNonDeleted();

        // Adiciona a lista ao "model", que é um "pacote" de dados para a página HTML
        model.addAttribute("carros", carros);

        // Retorna o nome do arquivo HTML que deve ser exibido
        return "index";
    }

    @GetMapping("/deletar/{id}")
    public String deletarPage(@PathVariable Long id) {
        service.delete(id);
        // A questão pede para redirecionar para /index após a remoção
        return "redirect:/index";
    }

    @GetMapping("/restaurar/{id}")
    public String restaurarPage(@PathVariable Long id) {
        service.restore(id);
        // Após restaurar, faz sentido voltar para a página de admin
        return "redirect:/admin";
    }

    @GetMapping("/editar/{id}")
    public String getEditarPage(@PathVariable Long id, Model model) {

        // Busca o carro pelo ID no serviço
        Optional<Carro> optionalCarro = service.findById(id);

        // Se o carro existir, ele é adicionado ao model e a página do formulário é exibida
        if (optionalCarro.isPresent()) {
            model.addAttribute("carro", optionalCarro.get());
            // Reutiliza o mesmo template do formulário de cadastro
            return "form-cadastro";
        } else {
            // Se o carro não for encontrado, redireciona para a página de admin
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        // Pede ao serviço a lista de TODOS os carros
        List<Carro> carros = service.findAll();

        // Adiciona a lista ao model para ser usada na página HTML
        model.addAttribute("carros", carros);

        // Retorna o nome do novo arquivo HTML da página de admin
        return "admin";
    }

    @GetMapping("/cadastro")
    public String getCadastroPage(Model model) {
        // Cria um objeto Carro vazio para ser preenchido pelo formulário
        Carro carro = new Carro();

        // Adiciona o objeto ao model para que o Thymeleaf possa usá-lo
        model.addAttribute("carro", carro);

        // Retorna o nome do novo arquivo HTML que vamos criar
        return "form-cadastro";
    }

    @PostMapping("/salvar")
    public String doSalvar(@ModelAttribute @Valid Carro carro, BindingResult bindingResult) {

        // Se houver erros de validação, retorna para a página do formulário
        if (bindingResult.hasErrors()) {
            return "form-cadastro";
        }

        // Se não houver erros, salva o carro usando o serviço
        service.save(carro);

        // Redireciona o usuário para a página inicial
        return "redirect:/index";
    }
}