package br.rraminelli.service.impl;

import br.rraminelli.dto.email.SendEmailDto;
import br.rraminelli.service.SendEmailService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Slf4j
@ApplicationScoped
public class SendEmailServiceImpl implements SendEmailService {

    final Mailer mailer;

    public SendEmailServiceImpl(Mailer mailer) {
        this.mailer = mailer;
    }


    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void send(final SendEmailDto sendEmailDto) {

        log.info("INICIO - Envio email - " + sendEmailDto);

        Uni.createFrom()
                .item(sendEmailDto)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .subscribe()
                .with(this::sendMailAsync, Throwable::printStackTrace);

        log.info("FIM - Envio email - " + sendEmailDto);

    }

    private Uni<Void> sendMailAsync(final SendEmailDto sendEmailDto) {

        final Mail mail = Mail.withText(
                sendEmailDto.getPara(),
                sendEmailDto.getAssunto(),
                sendEmailDto.getMensagem()
        );

        mailer.send(mail);

        return Uni.createFrom().voidItem();
    }

}
