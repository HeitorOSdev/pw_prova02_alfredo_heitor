package br.ufrn.tads.web_car_store.repository;

import br.ufrn.tads.web_car_store.domain.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {
}