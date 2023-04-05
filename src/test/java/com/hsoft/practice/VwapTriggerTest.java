package com.hsoft.practice;

import org.junit.jupiter.api.Test;

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
        assertAll(productId,
                () -> assertEquals(quantity,
                        value.get(0).getQuantity(),
                        "The quantity is incorrect"
                ),
                () -> assertEquals(price,
                        value.get(0).getPrice(),
                        "The price is incorrect"
                )
        );

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
        assertAll(productId,
                () -> assertEquals(quantity1,
                        value.get(0).getQuantity(),
                        "The first quantity added is incorrect"
                ),
                () -> assertEquals(price1,
                        value.get(0).getPrice(),
                        "The first price added is incorrect"
                ),
                () -> assertEquals(quantity2,
                        value.get(1).getQuantity(),
                        "The second quantity added is incorrect"
                ),
                () -> assertEquals(price2,
                        value.get(1).getPrice(),
                        "The second price added is incorrect"
                )
        );
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
        assertAll(productId1,
                () -> assertEquals(quantity1,
                        valueFirstProduct.get(0).getQuantity(),
                        "The quantity of first product added is incorrect"
                ),
                () -> assertEquals(price1,
                        valueFirstProduct.get(0).getPrice(),
                        "The price of first product added is incorrect"
                )
        );

        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = vwapTrigger.productTransactions.get(productId2);
        assertAll(productId2,
                () -> assertEquals(quantity2,
                        valueSecondProduct.get(0).getQuantity(),
                        "The quantity of second product added is incorrect"
                ),
                () -> assertEquals(price2,
                        valueSecondProduct.get(0).getPrice(),
                        "The price of second product added is incorrect"
                )
        );
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
        assertAll(productId1,
                () -> assertEquals(quantity1,
                        valueFirstProduct.get(0).getQuantity(),
                        "The first quantity of first product added is incorrect"
                ),
                () -> assertEquals(price1,
                        valueFirstProduct.get(0).getPrice(),
                        "The first price of first product added is incorrect"
                ),
                () -> assertEquals(quantity1Bis,
                        valueFirstProduct.get(1).getQuantity(),
                        "The second quantity of first product added is incorrect"
                ),
                () -> assertEquals(price1Bis,
                        valueFirstProduct.get(1).getPrice(),
                        "The second price of first product added is incorrect"
                )
        );

        assertTrue(
                vwapTrigger.productTransactions.containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = vwapTrigger.productTransactions.get(productId2);
        assertAll(productId2,
                () -> assertEquals(quantity2,
                        valueSecondProduct.get(0).getQuantity(),
                        "The quantity of second product added is incorrect"
                ),
                () -> assertEquals(price2,
                        valueSecondProduct.get(0).getPrice(),
                        "The price of second product added is incorrect"
                )
        );
    }
}
