package br.rraminelli.service.impl.pedido;

import br.rraminelli.dto.email.SendEmailDto;
import br.rraminelli.model.Cliente;
import br.rraminelli.model.Pedido;
import br.rraminelli.model.enums.StatusPedidoEnum;
import br.rraminelli.service.ProcessarPedidoService;
import br.rraminelli.service.SendEmailService;
import io.quarkus.arc.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;

@Priority(20)
@ApplicationScoped
public class ProcessarPedidoEnvioEmailCliente implements ProcessarPedidoService {

    final SendEmailService sendEmailService;

    public ProcessarPedidoEnvioEmailCliente(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void processar(Pedido pedido) {

        final String mensagem = this.criarMensagem(pedido);

        final SendEmailDto sendEmailDto = SendEmailDto.builder()
                .assunto("Status do pedido")
                .para(pedido.getCliente().getEmail())
                .mensagem(mensagem)
                .build();

        sendEmailService.send(sendEmailDto);

    }

    private String criarMensagem(final Pedido pedido) {
        final Cliente cliente = pedido.getCliente();

        final String dataPedido =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(pedido.getDataPedido());

        final StringBuilder mensagem = new StringBuilder();
        mensagem.append(cliente.getNome()).append(", ").append("\n");
        mensagem.append("Pedido: ").append(pedido.getId()).append("\n");
        mensagem.append("Data: ").append(dataPedido).append("\n");
        mensagem.append("Produtos: ").append("\n");
        pedido.getItens().forEach(item -> {
            mensagem.append(item.getQuantidade()).append("x ").append(item.getProduto().getNome()).append("\n");
        });

        mensagem.append("Status: ").append(pedido.getStatus()).append("\n");
        if (StatusPedidoEnum.CANCELADO.equals(pedido.getStatus())) {
            mensagem.append(pedido.getMensagemStatus()).append("\n");;
        }

        return mensagem.toString();
    }

}
