package com.hsoft.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Vwap {

    private final Map<String, Double> VWAP;
    private final Map<String, Queue<Transaction>> productTransactions;

    public Vwap() {
        this.VWAP = new HashMap<>();
        this.productTransactions = new HashMap<>();
    }

    public Map<String, Double> getVWAP() {
        return VWAP;
    }

    public Map<String, Queue<Transaction>> getProductTransactions() {
        return productTransactions;
    }

    public synchronized void mainVwap(String productId, long quantity, Double price) {
        saveTransaction(productId, quantity, price);
        var vwapResult = computeVwap(productTransactions.get(productId));
        VWAP.put(productId, vwapResult);
    }

    private synchronized void saveTransaction(String productId, long quantity, double price) {
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
