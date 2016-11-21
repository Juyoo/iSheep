package org.isheep.entity;

import org.isheep.entity.embeddable.Address;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by anthony on 08/11/16.
 */
public class AddressHibernateValidatorTests {

    public static Address createValid() {
        return new Address("6 Bis", "Rue du mouton", "63000", "Clermont-Ferrand");
    }

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateStreetNumber() {
        final Address entity = createValid();
        entity.setStreetNumber("a");
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);

        entity.setStreetNumber("");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);

        entity.setStreetNumber("6C");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();

        entity.setStreetNumber("29 Bis");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateStreet() {
        final Address entity = createValid();
        entity.setStreet("");
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setStreet("Rue du boulevard");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateZip() {
        final Address entity = createValid();
        entity.setZip("115");
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 5 digits");

        entity.setZip("aaaaa");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 5 digits");

        entity.setZip("631234");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 5 digits");

        entity.setZip("63000");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateCity() {
        final Address entity = createValid();
        entity.setCity("");
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setCity("Clermont-Ferrand");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();
    }

}
