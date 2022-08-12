package br.rraminelli.model;

import br.rraminelli.model.enums.StatusPedidoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @NotNull
    private LocalDateTime dataPedido;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    private String mensagemStatus;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens;

}
