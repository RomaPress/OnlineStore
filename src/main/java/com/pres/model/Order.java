package com.pres.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 * Represents an order in DB. To create an instance of this class use Builder.
 *
 * @author Pres Roman
 **/
public class Order implements Serializable {
    private static final long serialVersionUID = 74645L;
    private int id;
    private User user;
    private List<Product> products;
    private String dateTime;
    private Status status;
    private String invoiceNumber;
    private double total;
    private String city;
    private int postOffice;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(int postOffice) {
        this.postOffice = postOffice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equalsIgnoreCase(Order.Status.REGISTERED.value())){
            this.status = Order.Status.REGISTERED;
        }else if (status.equalsIgnoreCase(Order.Status.PAID.value())){
            this.status = Order.Status.PAID;
        }else if (status.equalsIgnoreCase(Order.Status.CANCELED.value())){
            this.status = Order.Status.CANCELED;
        }
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Represents an order status.
     */
    public enum Status {
        PAID("PAID"), REGISTERED("REGISTERED"), CANCELED("CANCELED");

        private final String value;

        Status(String value){
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

}
