package br.rraminelli.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String logradouro;
    @NotNull @NotBlank
    private String bairro;
    @NotNull @NotBlank
    private String cidade;
    @NotNull @NotBlank
    private String uf;
    @NotNull @NotBlank
    private String cep;

    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;

}
