package vadebici.servico_externo.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import vadebici.servico_externo.exception.GenericApiException;

import java.util.Set;

public class Validacao {

    private Validacao(){}
    private static final Validator validar;

    static {
        validar = Validation
                .byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }
    public static void validarCampos(Object instancia) {
        verificarConstraints(validar.validate(instancia));
    }

    private static void verificarConstraints(Set<ConstraintViolation<Object>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            throw new GenericApiException(422, constraintViolations.iterator().next().getMessage());
        }
    }
}
