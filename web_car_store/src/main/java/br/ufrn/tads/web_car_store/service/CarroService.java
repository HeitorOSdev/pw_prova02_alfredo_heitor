package br.ufrn.tads.web_car_store.service;

import br.ufrn.tads.web_car_store.domain.Carro;
import br.ufrn.tads.web_car_store.repository.CarroRepository;
import org.springframework.stereotype.Service;
import java.util.Date; // Adicione esta importação no topo do arquivo
import java.util.Optional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarroService {

    private final CarroRepository repository;

    // Injeção de Dependência via construtor
    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    // Método para listar apenas os carros não deletados (isDeleted == null)
    public List<Carro> findAllNonDeleted() {
        // Pega todos os carros do banco
        List<Carro> todosOsCarros = repository.findAll();
        // Filtra a lista para retornar apenas os que não têm data de deleção
        return todosOsCarros.stream()
                .filter(carro -> carro.getIsDeleted() == null)
                .collect(Collectors.toList());
    }

    public List<Carro> findAll() {
        return repository.findAll();
    }

    public Optional<Carro> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        // Busca o carro pelo ID
        findById(id).ifPresent(carro -> {
            // Seta a data atual no campo isDeleted
            carro.setIsDeleted(new Date());
            // Salva a alteração
            repository.save(carro);
        });
    }

    public void restore(Long id) {
        // Busca o carro pelo ID
        findById(id).ifPresent(carro -> {
            // Remove a data do campo isDeleted, tornando-o nulo
            carro.setIsDeleted(null);
            // Salva a alteração
            repository.save(carro);
        });
    }

    public Carro save(Carro c) {
        // Regra de negócio da Questão 5: selecionar imagem aleatória se for um novo cadastro
        if (c.getId() == null) {
            List<String> imagensDisponiveis = List.of("carro1.jpg", "carro2.jpg", "carro3.png"); // Use os nomes dos seus arquivos
            Random random = new Random();
            int indiceAleatorio = random.nextInt(imagensDisponiveis.size());
            c.setImageUrl(imagensDisponiveis.get(indiceAleatorio));
        }
        return repository.save(c);
    }
    // Futuramente, outros métodos virão aqui (salvar, buscar por id, deletar, etc.)
}