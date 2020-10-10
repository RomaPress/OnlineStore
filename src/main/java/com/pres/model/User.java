package com.pres.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 4464L;

    private int id;
    private String firstName;
    private String lastName;
    private Role role;
    private String phoneNumber;
    private String login;
    private String password;
    private String city;
    private int postOffice;

    private User(){
    }

    private User(Builder builder){
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
        this.phoneNumber = builder.phoneNumber;
        this.login = builder.login;
        this.password = builder.password;
        this.city = builder.city;
        this.postOffice = builder.postOffice;
    }

    public String getCity() {
        return city;
    }

    public int getPostOffice() {
        return postOffice;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

     public static class Builder{
        private int id;
        private String firstName;
        private String lastName;
        private Role role;
        private String phoneNumber;
        private String login;
        private String password;
         private String city;
         private int postOffice;

         public User build(){
             return new User(this);
         }

         public Builder setCity(String city) {
             this.city = city;
             return this;
         }

         public Builder setPostOffice(int postOffice) {
             this.postOffice = postOffice;
             return this;
         }

         public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setRole(String role) {
            if (role.equalsIgnoreCase(Role.ADMIN.value())){
                this.role = Role.ADMIN;
                return this;
            }else if (role.equalsIgnoreCase(Role.USER.value())){
                this.role = Role.USER;
                return this;
            }else{
                this.role = Role.UNKNOWN;
                return this;
            }
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
    }

    public enum Role {
        UNKNOWN("UNKNOWN"), ADMIN("ADMIN"), USER("USER"), BLOCK("BLOCK");

        private final String value;

        Role(String value){
            this.value = value;
        }

        public String value() {
            return value;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

