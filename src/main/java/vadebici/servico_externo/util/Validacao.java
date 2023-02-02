package vadebici.servico_externo.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import vadebici.exception.GenericApiException;

import java.util.Set;

public class Validacao {
    private static final jakarta.validation.Validator validator;

    static {
        validator = Validation
                .byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }

    private Validacao() {//teste

    }
    public static void validarCampos(Object instancia) {
        verificarConstraints(validator.validate(instancia));
    }


    private static void verificarConstraints(Set<ConstraintViolation<Object>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            throw new GenericApiException(422, constraintViolations.iterator().next().getMessage());
        }
    }
}
