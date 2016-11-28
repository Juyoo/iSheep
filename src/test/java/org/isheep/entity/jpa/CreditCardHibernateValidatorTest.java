package org.isheep.entity.jpa;

import org.isheep.config.javax.validation.groups.JPAValidationGroup;
import org.isheep.entity.embeddable.CreditCard;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by anthony on 08/11/16.
 */
public class CreditCardHibernateValidatorTest {

    public static CreditCard createValid() {
        return new CreditCard("Amazon", "5105105105105100", "563", 5, 2018);
    }

    private static Validator validator;
    private final Class[] validationGroups = new Class[] { JPAValidationGroup.class, Default.class };

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateName() {
        final CreditCard entity = createValid();
        entity.setOwnerName("");
        final Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

    @Test
    public void shouldValidateNumber() {
        final CreditCard entity = createValid();
        entity.setNumber("13");
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("size must be between 16 and 16");

        entity.setNumber("13232156465484984251544584464656");
        constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("size must be between 16 and 16");
    }

    @Test
    public void shouldValidateCSC() {
        final CreditCard entity = createValid();
        entity.setCsc("13");
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 3 digits");

        entity.setCsc("adc");
        constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 3 digits");

        entity.setCsc("1325");
        constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 3 digits");
    }

    @Test
    public void shouldvalidateMonthExpire() {
        final CreditCard entity = createValid();
        entity.setMonthExpire(0);
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("must be greater than or equal to 1");

        entity.setMonthExpire(13);
        constraintViolations = validator.validate(entity, validationGroups);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("must be less than or equal to 12");
    }

}
