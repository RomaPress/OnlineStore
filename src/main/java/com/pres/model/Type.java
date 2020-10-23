package com.pres.model;

import java.io.Serializable;

/**
 * Represents a type in DB.
 * To create an instance of this class use Builder.
 *
 * @author Pres Roman
 **/
public class Type implements Serializable {
    private static final long serialVersionUID = 63573L;

    private int id;
    private String name;

    private Type() {
    }

    private Type(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private int id;
        private String name;

        public Type build() {
            return new Type(this);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    }
}
