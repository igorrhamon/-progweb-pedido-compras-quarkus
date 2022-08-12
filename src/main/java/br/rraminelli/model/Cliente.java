package br.rraminelli.model;

import br.rraminelli.model.enums.PerfilEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome nao pode ser null")
    @NotEmpty(message = "Nome nao pode ser vazio")
    private String nome;

    @NotNull(message = "CPF nao pode ser null")
    @NotEmpty(message = "CPF nao pode ser vazio")
    private String cpf;

    @NotNull(message = "Email nao pode ser null")
    @NotEmpty(message = "Email nao pode ser vazio")
    @Email(message = "Email invalido")
    private String email;

    @JsonIgnoreProperties(allowGetters = true)
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private PerfilEnum perfil;

}
