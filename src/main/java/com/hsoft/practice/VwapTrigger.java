package com.hsoft.practice;


import com.hsoft.api.MarketDataListener;
import com.hsoft.api.PricingDataListener;
import com.hsoft.api.VwapTriggerListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Entry point for the candidate to resolve the exercise
 */
public class VwapTrigger implements PricingDataListener, MarketDataListener {

    private final VwapTriggerListener vwapTriggerListener;
    public final Map<String, Double> fairValueProduct = new HashMap<>();
    public final Map<String, Queue<Transaction>> productTransactions = new HashMap<>();

    /**
     * This constructor is mainly available to ease unit test by not having to provide a VwapTriggerListener
     */
    protected VwapTrigger() {
        this.vwapTriggerListener = (productId, vwap, fairValue) -> {
            // ignore
        };
    }

    public VwapTrigger(VwapTriggerListener vwapTriggerListener) {
        this.vwapTriggerListener = vwapTriggerListener;
    }

    @Override
    public void transactionOccurred(String productId, long quantity, double price) {
        // This method will be called when a new transaction is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'

        if (!productTransactions.containsKey(productId)) {
            productTransactions.put(productId, new LinkedList<>());
        } else if (productTransactions.get(productId).size() == 5) {
            Transaction elementRemoved = productTransactions.get(productId).remove();
        }
        productTransactions.get(productId).offer(new Transaction(quantity, price));

        //productTransactions.put(productId, new Transaction(quantity, price));
    }

    private Double computeVwap(Long quantity, Double price) {

        //    Double res = (quantity * price) / quantity;
        return 0.0;
    }

    @Override
    public void fairValueChanged(String productId, double fairValue) {
        // This method will be called when a new fair value is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'
        fairValueProduct.put(productId, fairValue);
    }
}