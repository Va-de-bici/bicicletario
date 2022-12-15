package vadebici.servico_aluguel.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import vadebici.servico_aluguel.exception.GenericApiException;
import vadebici.servico_aluguel.dom.*;

import java.util.Set;

public class Validacao {
    private Validacao() {

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

    public static void validaNacionalidade(Ciclista ciclista) {
        if(!ciclista.getNacionalidade().toUpperCase().startsWith("BRA")) {
            if (ciclista.getPassaporte() == null) throw new GenericApiException(405, "O campo 'passaporte' é obrigatório para usuários estrangeiros");
            verificarConstraints(validar.validate(ciclista.getPassaporte()), 405);

        } else {
            if (ciclista.getCpf() == null) throw new GenericApiException(405, "O campo 'cpf' é obrigatório para usuários brasileiros");
            if (ciclista.getCpf().length() != 11) throw new GenericApiException(405, "O campo 'cpf' está incorreto");
        }
    }


    public static void validaAtributos(Object instancia, int codeErrorHttp) {
        verificarConstraints(validar.validate(instancia), codeErrorHttp);
    }

    private static void verificarConstraints(Set<ConstraintViolation<Object>> constraintViolations, int codeErrorHttp) {
        if (!constraintViolations.isEmpty()) {
            throw new GenericApiException(codeErrorHttp, constraintViolations.iterator().next().getMessage());
        }
    }
}
