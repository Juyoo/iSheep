package org.isheep.entity;

import org.isheep.config.javax.validation.groups.JPAValidationGroup;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 15/11/2016.
 */
public class ParcelHibernateValidatorTest {

    public static Parcel createValid() {
        return new Parcel(32f, 15f, 10f, 500f);
    }

    private static Validator validator;
    private final Class[] validationGroups = new Class[] { JPAValidationGroup.class, Default.class };

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateWeight() {
        final Parcel entity = createValid();
        entity.setWeight(null);
        Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");

        entity.setWeight(32f);
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateWidth() {
        final Parcel entity = createValid();
        entity.setWidth(null);
        Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");

        entity.setWidth(32f);
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateHeight() {
        final Parcel entity = createValid();
        entity.setHeight(null);
        Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");

        entity.setHeight(32f);
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateDepth() {
        final Parcel entity = createValid();
        entity.setDepth(null);
        Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");

        entity.setDepth(32f);
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

}
