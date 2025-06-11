package br.ufrn.tads.web_car_store.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Atributo obrigatório

    @NotBlank(message = "O modelo não pode estar em branco.") // Exemplo de validação
    private String modelo;

    @NotBlank(message = "A marca não pode estar em branco.")
    private String marca;

    @NotNull(message = "O ano de fabricação é obrigatório.")
    private Integer anoFabricacao;

    @NotNull(message = "O preço é obrigatório.")
    @PositiveOrZero(message = "O preço não pode ser negativo.")
    private Double preco;

    private String imageUrl; // Atributo obrigatório

    private Date isDeleted; // Atributo obrigatório

    public Long getId() {
        return id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIsDeleted(Date isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getIsDeleted() {
        return isDeleted;
    }
}