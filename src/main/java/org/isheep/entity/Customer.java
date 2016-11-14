package org.isheep.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.CreditCard;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by anthony on 08/11/16.
 */
@Entity
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

    public Customer() {
    }

    public Customer(final String name, final Address address, final CreditCard creditCard) {
        this.name = name;
        this.address = address;
        this.creditCard = creditCard;
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
}
