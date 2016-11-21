package org.isheep.entity;

import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.Name;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by raymo on 15/11/2016.
 */
@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer sender;

    @Embedded
    private Name recipientName;

    @Embedded
    private Address recipientAddress;

    @NotNull
    private Float price;

    public Shipping() {
    }

    public Shipping(final Customer sender, final Name recipientName, final Address recipientAddress, final Float price) {
        this.sender = sender;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(final Customer sender) {
        this.sender = sender;
    }

    public Name getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final Name recipientName) {
        this.recipientName = recipientName;
    }

    public Address getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(final Address recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(final Float price) {
        this.price = price;
    }
}
