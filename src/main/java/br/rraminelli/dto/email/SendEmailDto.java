package br.rraminelli.dto.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SendEmailDto {

    private String para;
    private String assunto;
    private String mensagem;

}
