package com.hsoft.practice;


import com.hsoft.api.MarketDataListener;
import com.hsoft.api.PricingDataListener;
import com.hsoft.api.VwapTriggerListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for the candidate to resolve the exercise
 */
public class VwapTrigger implements PricingDataListener, MarketDataListener {

    private final VwapTriggerListener vwapTriggerListener;
    public final Map<String, Double> fairValueProduct = new HashMap<>();
    static Vwap vwap;

    /**
     * This constructor is mainly available to ease unit test by not having to provide a VwapTriggerListener
     */
    protected VwapTrigger() {
        this.vwapTriggerListener = (productId, vwap, fairValue) -> {
            // ignore
        };
        vwap = new Vwap();
    }

    public VwapTrigger(VwapTriggerListener vwapTriggerListener) {
        this.vwapTriggerListener = vwapTriggerListener;
        vwap = new Vwap();
    }

    @Override
    public void transactionOccurred(String productId, long quantity, double price) {
        // This method will be called when a new transaction is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'
        vwap.saveTransaction(productId, quantity, price);
        vwap.SaveAndComputeVwap(productId);
        compareVwapAndFairValue(productId);
    }

    @Override
    public synchronized void fairValueChanged(String productId, double fairValue) {
        // This method will be called when a new fair value is received
        // You can then perform your check
        // And, if matching the requirement, notify the event via 'this.vwapTriggerListener.vwapTriggered(xxx);'
        fairValueProduct.put(productId, fairValue);
        compareVwapAndFairValue(productId);
    }

    private synchronized void compareVwapAndFairValue(String productId) {
        if (vwap.getVWAP().containsKey(productId) && fairValueProduct.containsKey(productId) &&
                vwap.getVWAP().get(productId) > fairValueProduct.get(productId)) {
            vwapTriggerListener.vwapTriggered(productId, vwap.getVWAP().get(productId), fairValueProduct.get(productId));
        }
    }
}