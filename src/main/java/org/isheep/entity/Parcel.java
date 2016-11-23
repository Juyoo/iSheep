package org.isheep.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by raymo on 15/11/2016.
 */
@Entity
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(precision = 10, scale = 2)
    private Float width;

    @NotNull
    @Column(precision = 10, scale = 2)
    private Float height;

    @NotNull
    @Column(precision = 10, scale = 2)
    private Float depth;

    @NotNull
    @Column(precision = 10, scale = 2)
    private Float weight;

    Parcel() {
    }

    public Parcel(final Float width, final Float height, final Float depth, final Float weight) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
