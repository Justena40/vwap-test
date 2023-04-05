package com.hsoft.practice;

public class Transaction {
    private final long Quantity;
    private final Double Price;
    public Transaction(long quantity, double price) {
        this.Quantity = quantity;
        this.Price = price;
    }

    public Double getPrice() {
        return Price;
    }

    public long getQuantity() {
        return Quantity;
    }

}
