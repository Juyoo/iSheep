package org.isheep.entity;

import org.isheep.config.javax.validation.groups.JPAValidationGroup;
import org.isheep.entity.embeddable.Address;
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
public class CustomerHibernateValidatorTest {

    public static Customer createValid() {
        return new Customer("Amazon", AddressHibernateValidatorTest.createValid(), CreditCardHibernateValidatorTest.createValid(), "qsdqsdsqdsq");
    }

    private static Validator validator;
    private final Class[] validationGroups = new Class[] { JPAValidationGroup.class, Default.class };

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateName() {
        final Customer entity = createValid();
        entity.setName("");
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");

        entity.setName("Amazon");
        constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void shouldValidateAddress() {
        final Customer entity = createValid();
        entity.setAddress(null);
        final Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");
    }

    @Test
    public void shouldValidateAddressOnCascade() {
        final Customer entity = createValid();
        final Address address = AddressHibernateValidatorTest.createValid();
        address.setCity("");

        entity.setAddress(address);
        final Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

    @Test
    public void shouldValidateCreditCard() {
        final Customer entity = createValid();
        entity.setCreditCard(null);
        final Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");
    }

    @Test
    public void shouldValidateCreditCardOnCascade() {
        final Customer entity = createValid();
        final CreditCard creditCard = CreditCardHibernateValidatorTest.createValid();
        creditCard.setCsc("");

        entity.setCreditCard(creditCard);
        final Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("is supposed to be 3 digits");
    }

    @Test
    public void shouldValidateToken() {
        final Customer entity = createValid();
        entity.setToken(null);

        final Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(entity, validationGroups);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

}
