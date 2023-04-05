package com.hsoft.practice;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * One can add own unit tests here and/or in another class
 */
public class VwapTriggerTest {

    private final VwapTrigger vwapTrigger = new VwapTrigger();

    @Test
    void shouldAddFirstProductForFairValue() {
        // Given
        var productId = "P1";
        var fairValue = 11.0;

        //When
        vwapTrigger.fairValueChanged(productId, fairValue);

        //Then
        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = vwapTrigger.fairValueProduct.get(productId);
        assertEquals(fairValue, value);
    }

    @Test
    void shouldAddFirstProductWhenFairValueChanged() {
        // Given
        var productId = "P1";
        var fairValue = 11.0;
        var secondFairValue = 10.9;

        //When
        vwapTrigger.fairValueChanged(productId, fairValue);
        vwapTrigger.fairValueChanged(productId, secondFairValue);

        //Then
        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = vwapTrigger.fairValueProduct.get(productId);
        assertEquals(secondFairValue, value);
    }

    @Test
    void shouldAddSeveralProduct() {
        // Given
        var productId1 = "P1";
        var fairValue1 = 11.0;
        var productId2 = "P2";
        var fairValue2 = 12.5;

        //When
        vwapTrigger.fairValueChanged(productId1, fairValue1);
        vwapTrigger.fairValueChanged(productId2, fairValue2);

        //Then
        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var value = vwapTrigger.fairValueProduct.get(productId1);
        assertEquals(fairValue1, value);

        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        value = vwapTrigger.fairValueProduct.get(productId2);
        assertEquals(fairValue2, value);
    }

    @Test
    void shouldAddSeveralProductAndFirstFairValueChanged() {
        // Given
        var productId1 = "P1";
        var fairValue1 = 11.0;
        var fairValue1Bis = 10.9;
        var productId2 = "P2";
        var fairValue2 = 12.5;

        //When
        vwapTrigger.fairValueChanged(productId1, fairValue1);
        vwapTrigger.fairValueChanged(productId2, fairValue2);
        vwapTrigger.fairValueChanged(productId1, fairValue1Bis);

        //Then
        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var value = vwapTrigger.fairValueProduct.get(productId1);
        assertEquals(fairValue1Bis, value);

        assertTrue(
                vwapTrigger.fairValueProduct.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        value = vwapTrigger.fairValueProduct.get(productId2);
        assertEquals(fairValue2, value);
    }

    @Test
    void ShouldAddFirstTransaction() {
        //Given
        var productId = "P1";
        var quantity = 1000;
        var price = 10.0;

        //When
        vwapTrigger.transactionOccurred(productId, quantity, price);

        //Then
        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = vwapTrigger.productTransactions.get(productId);
        assert value.peek() != null;
        assertEquals(quantity, Objects.requireNonNull(value.peek()).getQuantity());
        assertEquals(price, Objects.requireNonNull(value.peek()).getPrice());
    }

    @Test
    void ShouldAddTwoTransactionsOnTheSameProduct() {
        //Given
        var productId = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;
        var quantity2 = 2000;
        var price2 = 11.0;

        //When
        vwapTrigger.transactionOccurred(productId, quantity1, price1);
        vwapTrigger.transactionOccurred(productId, quantity2, price2);

        //Then
        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = vwapTrigger.productTransactions.get(productId);
        assert value.peek() != null;
        assertEquals(quantity1, Objects.requireNonNull(value.peek()).getQuantity(), "The first quantity added is incorrect");
        assertEquals(price1, Objects.requireNonNull(value.peek()).getPrice(), "The first price added is incorrect");
        assertEquals(quantity2, value.stream().toList().get(1).getQuantity(), "The second quantity added is incorrect");
        assertEquals(price2, value.stream().toList().get(1).getPrice(), "The second price added is incorrect");
    }

    @Test
    void ShouldAddTransactionOnTwoDifferentProducts() {
        //Given
        var productId1 = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;
        var productId2 = "P2";
        var quantity2 = 500;
        var price2 = 12.0;

        //When
        vwapTrigger.transactionOccurred(productId1, quantity1, price1);
        vwapTrigger.transactionOccurred(productId2, quantity2, price2);

        //Then
        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var valueFirstProduct = vwapTrigger.productTransactions.get(productId1);
        assertEquals(quantity1, valueFirstProduct.stream().toList().get(0).getQuantity(), "The quantity of first product added is incorrect");
        assertEquals(price1, valueFirstProduct.stream().toList().get(0).getPrice(), "The price of first product added is incorrect");

        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = vwapTrigger.productTransactions.get(productId2);

        assertEquals(quantity2, valueSecondProduct.stream().toList().get(0).getQuantity(), "The quantity of second product added is incorrect");
        assertEquals(price2, valueSecondProduct.stream().toList().get(0).getPrice(), "The price of second product added is incorrect");
    }

    @Test
    void ShouldAddTwoTransactionsOnTwoDifferentProducts() {
        //Given
        var productId1 = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;
        var quantity1Bis = 2000;
        var price1Bis = 11.0;
        var productId2 = "P2";
        var quantity2 = 500;
        var price2 = 12.0;

        //When
        vwapTrigger.transactionOccurred(productId1, quantity1, price1);
        vwapTrigger.transactionOccurred(productId2, quantity2, price2);
        vwapTrigger.transactionOccurred(productId1, quantity1Bis, price1Bis);

        //Then
        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var valueFirstProduct = vwapTrigger.productTransactions.get(productId1);
        assertEquals(quantity1, valueFirstProduct.stream().toList().get(0).getQuantity(), "The first quantity of first product added is incorrect");
        assertEquals(price1, valueFirstProduct.stream().toList().get(0).getPrice(), "The first price of first product added is incorrect");
        assertEquals(quantity1Bis, valueFirstProduct.stream().toList().get(1).getQuantity(), "The second quantity of first product added is incorrect");
        assertEquals(price1Bis, valueFirstProduct.stream().toList().get(1).getPrice(), "The second price of first product added is incorrect");

        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = vwapTrigger.productTransactions.get(productId2);
        assertEquals(quantity2, valueSecondProduct.stream().toList().get(0).getQuantity(), "The quantity of second product added is incorrect");
        assertEquals(price2, valueSecondProduct.stream().toList().get(0).getPrice(), "The price of second product added is incorrect");
    }


    @Test
    void ShouldAddMoreThanFiveTransactionsOnProduct() {
        //Given
        var productId = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;
        var quantity2 = 2000;
        var price2 = 9.0;
        var quantity3 = 3000;
        var price3 = 8.5;
        var quantity4 = 4000;
        var price4 = 2.5;
        var quantity5 = 5000;
        var price5 = 6.8;
        var quantity6 = 6000;
        var price6 = 22.0;

        //When
        vwapTrigger.transactionOccurred(productId, quantity1, price1);
        vwapTrigger.transactionOccurred(productId, quantity2, price2);
        vwapTrigger.transactionOccurred(productId, quantity3, price3);
        vwapTrigger.transactionOccurred(productId, quantity4, price4);
        vwapTrigger.transactionOccurred(productId, quantity5, price5);
        vwapTrigger.transactionOccurred(productId, quantity6, price6);

        //Then
        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = vwapTrigger.productTransactions.get(productId);
        assert value.peek() != null;
        assertEquals(quantity2, value.stream().toList().get(0).getQuantity(), "The first quantity added is incorrect");
        assertEquals(price2, value.stream().toList().get(0).getPrice(), "The first price added is incorrect");
        assertEquals(quantity3, value.stream().toList().get(1).getQuantity(), "The second quantity added is incorrect");
        assertEquals(price3, value.stream().toList().get(1).getPrice(), "The second price added is incorrect");
        assertEquals(quantity4, value.stream().toList().get(2).getQuantity(), "The third quantity added is incorrect");
        assertEquals(price4, value.stream().toList().get(2).getPrice(), "The third price added is incorrect");
        assertEquals(quantity5, value.stream().toList().get(3).getQuantity(), "The fourth quantity added is incorrect");
        assertEquals(price5, value.stream().toList().get(3).getPrice(), "The fourth price added is incorrect");
        assertEquals(quantity6, value.stream().toList().get(4).getQuantity(), "The fifth quantity added is incorrect");
        assertEquals(price6, value.stream().toList().get(4).getPrice(), "The fifth price added is incorrect");
    }
}
