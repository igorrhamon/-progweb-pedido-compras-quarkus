package br.rraminelli.service;

import br.rraminelli.dto.email.SendEmailDto;
import io.smallrye.mutiny.Uni;

public interface SendEmailService {

    void send(SendEmailDto sendEmailDto);

}
