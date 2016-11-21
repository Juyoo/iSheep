package org.isheep.entity;

import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.Name;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.json.XMLTokener.entity;

/**
 * Created by raymo on 21/11/2016.
 */
public class ShippingHibernateValidatorTest {

    public static Shipping createValid() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        final Name name = NameHibernateValidatorTests.createValid();
        final Address address = AddressHibernateValidatorTests.createValid();
        final Parcel parcel = ParcelHibernateValidatorTest.createValid();
        return new Shipping(customer, name, address, parcel, 32.1f);
    }


    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldValidateSenderOnCascade() {
        final Shipping entity = createValid();
        entity.getSender().setName(null);
        final Set<ConstraintViolation<Shipping>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

    @Test
    public void shouldValidateRecipientNameOnCascade() {
        final Shipping entity = createValid();
        entity.getRecipientName().setFirstname(null);
        final Set<ConstraintViolation<Shipping>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

    @Test
    public void shouldValidateRecipientAddressOnCascade() {
        final Shipping entity = createValid();
        entity.getRecipientAddress().setCity(null);
        final Set<ConstraintViolation<Shipping>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be empty");
    }

    @Test
    public void shouldValidateParcelOnCascade() {
        final Shipping entity = createValid();
        entity.getParcel().setHeight(null);
        final Set<ConstraintViolation<Shipping>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("may not be null");
    }

    @Test
    public void shouldValidatePrice() {
        final Shipping entity = createValid();
        entity.setPrice(-1f);
        Set<ConstraintViolation<Shipping>> constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("must be greater than or equal to 0");

        entity.setPrice(22f);
        constraintViolations = validator.validate(entity);
        assertThat(constraintViolations).isEmpty();
    }



}
