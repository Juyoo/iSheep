package org.isheep.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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

    public void setId(final Long id) {
        this.id = id;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(final Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(final Float height) {
        this.height = height;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(final Float depth) {
        this.depth = depth;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(final Float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Parcel parcel = (Parcel) o;
        return Objects.equal(id, parcel.id) &&
                Objects.equal(width, parcel.width) &&
                Objects.equal(height, parcel.height) &&
                Objects.equal(depth, parcel.depth) &&
                Objects.equal(weight, parcel.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, width, height, depth, weight);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("width", width)
                .add("height", height)
                .add("depth", depth)
                .add("weight", weight)
                .toString();
    }
}
