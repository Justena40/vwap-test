package com.hsoft.practice;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * One can add own unit tests here and/or in another class
 */
public class ProductsTest {

    private final Products products = new Products();

    @Test
    void shouldAddFirstProductForFairValue() {
        // Given
        var productId = "P1";
        var fairValue = 11.0;

        //When
        products.setFairValueProduct(productId, fairValue);

        //Then
        assertTrue(
                products.getFairValueProduct().containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = products.getFairValueProduct().get(productId);
        assertEquals(fairValue, value);
    }

    @Test
    void shouldAddFirstProductWhenFairValueChanged() {
        // Given
        var productId = "P1";
        var fairValue = 11.0;
        var secondFairValue = 10.9;

        //When
        products.setFairValueProduct(productId, fairValue);
        products.setFairValueProduct(productId, secondFairValue);

        //Then
        assertTrue(
                products.getFairValueProduct().containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = products.getFairValueProduct().get(productId);
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
        products.setFairValueProduct(productId1, fairValue1);
        products.setFairValueProduct(productId2, fairValue2);

        //Then
        assertTrue(
                products.getFairValueProduct().containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var value = products.getFairValueProduct().get(productId1);
        assertEquals(fairValue1, value);

        assertTrue(
                products.getFairValueProduct().containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        value = products.getFairValueProduct().get(productId2);
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
        products.setFairValueProduct(productId1, fairValue1);
        products.setFairValueProduct(productId2, fairValue2);
        products.setFairValueProduct(productId1, fairValue1Bis);

        //Then
        assertTrue(
                products.getFairValueProduct().containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var value = products.getFairValueProduct().get(productId1);
        assertEquals(fairValue1Bis, value);

        assertTrue(
                products.getFairValueProduct().containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        value = products.getFairValueProduct().get(productId2);
        assertEquals(fairValue2, value);
    }

    @Test
    void ShouldAddFirstTransaction() {
        //Given
        var productId = "P1";
        var quantity = 1000;
        var price = 10.0;

        //When
        products.saveTransaction(productId, quantity, price);

        //Then
        assertTrue(
                products.getProductTransactions().containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = products.getProductTransactions().get(productId);
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
        products.saveTransaction(productId, quantity1, price1);
        products.saveTransaction(productId, quantity2, price2);

        //Then
        assertTrue(
                products.getProductTransactions().containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = products.getProductTransactions().get(productId);
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
        products.saveTransaction(productId1, quantity1, price1);
        products.saveTransaction(productId2, quantity2, price2);

        //Then
        assertTrue(
                products.getProductTransactions().containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var valueFirstProduct = products.getProductTransactions().get(productId1);
        assertEquals(quantity1, valueFirstProduct.stream().toList().get(0).getQuantity(), "The quantity of first product added is incorrect");
        assertEquals(price1, valueFirstProduct.stream().toList().get(0).getPrice(), "The price of first product added is incorrect");

        assertTrue(
                products.getProductTransactions().containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = products.getProductTransactions().get(productId2);

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
        products.saveTransaction(productId1, quantity1, price1);
        products.saveTransaction(productId2, quantity2, price2);
        products.saveTransaction(productId1, quantity1Bis, price1Bis);

        //Then
        assertTrue(
                products.getProductTransactions().containsKey(productId1),
                () -> String.format("The map doesn't contain the key: %s", productId1)
        );

        var valueFirstProduct = products.getProductTransactions().get(productId1);
        assertEquals(quantity1, valueFirstProduct.stream().toList().get(0).getQuantity(), "The first quantity of first product added is incorrect");
        assertEquals(price1, valueFirstProduct.stream().toList().get(0).getPrice(), "The first price of first product added is incorrect");
        assertEquals(quantity1Bis, valueFirstProduct.stream().toList().get(1).getQuantity(), "The second quantity of first product added is incorrect");
        assertEquals(price1Bis, valueFirstProduct.stream().toList().get(1).getPrice(), "The second price of first product added is incorrect");

        assertTrue(
                products.getProductTransactions().containsKey(productId2),
                () -> String.format("The map doesn't contain the key: %s", productId2)
        );

        var valueSecondProduct = products.getProductTransactions().get(productId2);
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
        products.saveTransaction(productId, quantity1, price1);
        products.saveTransaction(productId, quantity2, price2);
        products.saveTransaction(productId, quantity3, price3);
        products.saveTransaction(productId, quantity4, price4);
        products.saveTransaction(productId, quantity5, price5);
        products.saveTransaction(productId, quantity6, price6);

        //Then
        assertTrue(
                products.getProductTransactions().containsKey(productId),
                () -> String.format("The map doesn't contain the key: %s", productId)
        );

        var value = products.getProductTransactions().get(productId);
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

    @Test
    void ShouldComputeVWAPForOneTransaction() {
        //Given
        var productId = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;

        //When
        products.saveTransaction(productId, quantity1, price1);
        products.saveAndComputeVwap(productId);

        //Then
        assertEquals(price1, products.getVwap().get(productId), "The VWAP computation is incorrect");
    }

    @Test
    void ShouldComputeVWAPForTwoTransaction() {
        //Given
        var productId = "P1";
        var quantity1 = 1000;
        var price1 = 10.0;
        var quantity2 = 2000;
        var price2 = 11.0;

        //When
        products.saveTransaction(productId, quantity1, price1);
        products.saveTransaction(productId, quantity2, price2);
        products.saveAndComputeVwap(productId);

        //Then
        var vwapResult = (quantity1 * price1 + quantity2 * price2) / (quantity1 + quantity2);
        assertEquals(vwapResult, products.getVwap().get(productId), "The VWAP computation is incorrect");
    }

    @Test
    void ShouldComputeVWAPForTwoProducts() {
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
        products.saveTransaction(productId1, quantity1, price1);
        products.saveTransaction(productId2, quantity2, price2);
        products.saveTransaction(productId1, quantity1Bis, price1Bis);
        products.saveAndComputeVwap(productId1);
        products.saveAndComputeVwap(productId2);

        //Then
        var vwapResultProductId1 = (quantity1 * price1 + quantity1Bis * price1Bis) / (quantity1 + quantity1Bis);
        assertEquals(vwapResultProductId1, products.getVwap().get(productId1), "The VWAP computation for the first product is incorrect");
        assertEquals(price2, products.getVwap().get(productId2), "The VWAP computation for the second product is incorrect");
    }
}
