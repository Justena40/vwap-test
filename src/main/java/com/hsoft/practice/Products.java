package com.hsoft.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Products {
    private final Map<String, Double> vwap;
    private final Map<String, Queue<Transaction>> productTransactions;
    private final Map<String, Double> fairValueProduct;

    public Products() {
        this.vwap = new HashMap<>();
        this.productTransactions = new HashMap<>();
        this.fairValueProduct = new HashMap<>();
    }

    public Map<String, Double> getVwap() {
        return vwap;
    }

    public Map<String, Queue<Transaction>> getProductTransactions() {
        return productTransactions;
    }

    public Map<String, Double> getFairValueProduct() {
        return fairValueProduct;
    }

    public void setFairValueProduct(String productId, Double fairValue) {
        fairValueProduct.put(productId, fairValue);
    }

    public synchronized void saveAndComputeVwap(String productId) {
        var vwapResult = computeVwap(getProductTransactions().get(productId));
        vwap.put(productId, vwapResult);
    }

    public synchronized void saveTransaction(String productId, long quantity, double price) {
        if (!productTransactions.containsKey(productId)) {
            productTransactions.put(productId, new LinkedList<>());
        } else if (productTransactions.get(productId).size() == 5) {
            // catch exception ??
            Transaction elementRemoved = productTransactions.get(productId).remove();
        }
        productTransactions.get(productId).offer(new Transaction(quantity, price));
    }

    private Double computeVwap(Queue<Transaction> transactions) {
        var dividend = 0.0;
        var divisor = 0L;

        for (Transaction transaction : transactions) {
            dividend += transaction.getPrice() * transaction.getQuantity();
            divisor += transaction.getQuantity();
        }
        return dividend / divisor;
    }
}
