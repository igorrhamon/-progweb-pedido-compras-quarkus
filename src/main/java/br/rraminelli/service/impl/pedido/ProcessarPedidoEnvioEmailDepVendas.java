package br.rraminelli.service.impl.pedido;

import br.rraminelli.dto.email.SendEmailDto;
import br.rraminelli.model.Pedido;
import br.rraminelli.model.enums.StatusPedidoEnum;
import br.rraminelli.service.ProcessarPedidoService;
import br.rraminelli.service.SendEmailService;
import io.quarkus.arc.Priority;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;

@Priority(10)
@ApplicationScoped
public class ProcessarPedidoEnvioEmailDepVendas implements ProcessarPedidoService {

    final SendEmailService sendEmailService;
    final String emailDepVendas;

    public ProcessarPedidoEnvioEmailDepVendas(SendEmailService sendEmailService,
                                              @ConfigProperty(name = "email.vendas") String emailDepVendas) {
        this.sendEmailService = sendEmailService;
        this.emailDepVendas = emailDepVendas;
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void processar(Pedido pedido) {

        if (StatusPedidoEnum.CANCELADO.equals(pedido.getStatus())) {
            return;
        }

        final String mensagem = this.criarMensagem(pedido);

        final SendEmailDto sendEmailDto = SendEmailDto.builder()
                .assunto("Novo pedido de compras")
                .para(emailDepVendas)
                .mensagem(mensagem)
                .build();

        sendEmailService.send(sendEmailDto);

    }

    private String criarMensagem(final Pedido pedido) {

        final String dataPedido =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(pedido.getDataPedido());

        //pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        final StringBuilder mensagem = new StringBuilder();
        mensagem.append("Cliente: ").append(pedido.getCliente().getNome()).append("\n");
        mensagem.append("Pedido: ").append(pedido.getId()).append("\n");
        mensagem.append("Data: ").append(dataPedido).append("\n");
        mensagem.append("Produtos: ").append("\n");
        pedido.getItens().forEach(item -> {
            mensagem.append(item.getQuantidade()).append("x ").append(item.getProduto().getNome()).append("\n");
        });
        return mensagem.toString();
    }

}
