package org.isheep.entity;

import org.isheep.entity.embeddable.Name;
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
public class NameHibernateValidatorTests {

    public static Name createValid() {
        return new Name("Scott", "Tiger");
    }

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateFirstname() {
        final Name entity = createValid();
        entity.setFirstname("");
        Set<ConstraintViolation<Name>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setFirstname("Marc");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void shouldValidateLastname() {
        final Name entity = createValid();
        entity.setLastname("");
        Set<ConstraintViolation<Name>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setLastname("Tiger");
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

}
