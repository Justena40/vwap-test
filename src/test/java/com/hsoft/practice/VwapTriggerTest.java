package com.hsoft.practice;

import com.hsoft.api.VwapTriggerListener;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;



/**
 * One can add own unit tests here and/or in another class
 */
public class VwapTriggerTest {

    private final VwapTriggerListener mockedVwapTriggerListener;

    private final VwapTrigger vwapTrigger;

    public VwapTriggerTest() {
        mockedVwapTriggerListener = mock(VwapTriggerListener.class);
        vwapTrigger = new VwapTrigger(mockedVwapTriggerListener);
    }

    @Test
    void shouldTriggeredVwapListenerMethod() {
        // Given
        var productId = "P1";
        var quantity = 1000;
        var price = 19.0;
        var fairValue = 10.0;

        // When
        vwapTrigger.fairValueChanged(productId, fairValue);
        vwapTrigger.transactionOccurred(productId, quantity, price);

        // Then
        verify(mockedVwapTriggerListener, times(1)).vwapTriggered(productId, 19.0, fairValue);
    }


    @Test
    void shouldNotTriggeredVwapListenerMethod() {
        // Given
        var productId = "P1";
        var quantity = 1000;
        var price = 1000.0;
        var fairValue = 1.0;
        VwapTriggerListener mockedVwapTriggerListener = mock(VwapTriggerListener.class);

        // When
        vwapTrigger.fairValueChanged(productId, fairValue);
        vwapTrigger.transactionOccurred(productId, quantity, price);

        // Then
        verify(mockedVwapTriggerListener, times(0)).vwapTriggered(productId, 19.0, fairValue);
    }
}
