package vadebici.servico_aluguel.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.dom.*;

import java.util.Set;

public class Validacao {
    public Validacao() {
        // Não é necessário nenhum parâmetro para instanciar Validacao,
        // pois seu funcionamento depende apenas dos parâmetros recebidos pelos seus métodos.
    }

    private static final Validator validar;

    static {
        validar = Validation
                .byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }

    public void validaNacionalidade(Ciclista ciclista) {
        if(!ciclista.getNacionalidade().toUpperCase().startsWith("BRA")) {
            if (ciclista.getPassaporte() == null) throw new GenericApiException(405, "O campo 'passaporte' é obrigatório para usuários estrangeiros");
            verificarConstraints(validar.validate(ciclista.getPassaporte()), 405);

        } else {
            if (ciclista.getCpf() == null) throw new GenericApiException(405, "O campo 'cpf' é obrigatório para usuários brasileiros");
            if (ciclista.getCpf().length() != 11 || !ciclista.getCpf().matches("[0-9]+")) throw new GenericApiException(405, "O campo 'cpf' está incorreto");
        }
    }

    public void validaNacionalidadeJson(CiclistaJsonApi ciclista) {
        if(!ciclista.getNacionalidade().toUpperCase().startsWith("BRA")) {
            if (ciclista.getPassaporte() == null) throw new GenericApiException(405, "O campo 'passaporte' é obrigatório para usuários estrangeiros");
            verificarConstraints(validar.validate(ciclista.getPassaporte()), 405);

        } else {
            if (ciclista.getCpf() == null) throw new GenericApiException(405, "O campo 'cpf' é obrigatório para usuários brasileiros");
            if (ciclista.getCpf().length() != 11 || !ciclista.getCpf().matches("[0-9]+")) throw new GenericApiException(405, "O campo 'cpf' está incorreto");
        }
    }


    public void validaAtributos(Object instancia, int codeErrorHttp) {
        verificarConstraints(validar.validate(instancia), codeErrorHttp);
    }

    private void verificarConstraints(Set<ConstraintViolation<Object>> constraintViolations, int codeErrorHttp) {
        if (!constraintViolations.isEmpty()) {
            throw new GenericApiException(codeErrorHttp, constraintViolations.iterator().next().getMessage());
        }
    }
}
