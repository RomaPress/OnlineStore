package com.pres.model;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static final long serialVersionUID = 1787867675L;

    private int id;
    private String name;
    private double price;
    private int amount;
    private String description;
    private Type type;

    private Product(){}

    private Product(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.amount = builder.amount;
        this.description = builder.description;
        this.type = builder.type;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder{
        private int id;
        private String name;
        private double price;
        private int amount;
        private String description;
        private Type type;

        public Product build(){
            return new Product(this);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
