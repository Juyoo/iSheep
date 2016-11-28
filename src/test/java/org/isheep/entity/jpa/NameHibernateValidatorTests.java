package org.isheep.entity.jpa;

import org.isheep.config.javax.validation.groups.JPAValidationGroup;
import org.isheep.entity.embeddable.Name;
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
public class NameHibernateValidatorTests {

    public static Name createValid() {
        return new Name("Scott", "Tiger");
    }

    private static Validator validator;
    private final Class[] validationGroups = new Class[] { JPAValidationGroup.class, Default.class };

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateFirstname() {
        final Name entity = createValid();
        entity.setFirstname("");
        Set<ConstraintViolation<Name>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setFirstname("Marc");
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateLastname() {
        final Name entity = createValid();
        entity.setLastname("");
        Set<ConstraintViolation<Name>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setLastname("Tiger");
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

}
