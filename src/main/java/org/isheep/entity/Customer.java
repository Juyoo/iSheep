package org.isheep.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.isheep.config.javax.validation.groups.JPAValidationGroup;
import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.CreditCard;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by anthony on 08/11/16.
 */
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "token")
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    @Valid
    @NotNull
    @Embedded
    private Address address;

    @Valid
    @NotNull
    @Embedded
    private CreditCard creditCard;

    @NotBlank(groups = JPAValidationGroup.class)
    private String token;

    Customer() {
    }

    public Customer(final String name, final Address address, final CreditCard creditCard, final String token) {
        this.name = name;
        this.address = address;
        this.creditCard = creditCard;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Customer customer = (Customer) o;
        return Objects.equal(id, customer.id) &&
                Objects.equal(name, customer.name) &&
                Objects.equal(address, customer.address) &&
                Objects.equal(creditCard, customer.creditCard) &&
                Objects.equal(token, customer.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, address, creditCard, token);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("address", address)
                .add("creditCard", creditCard)
                .add("token", token)
                .toString();
    }
}
