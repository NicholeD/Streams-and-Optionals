package com.kenzie.optionals.productinventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ProductInventoryTest {

    ProductUtility productUtility;

    @BeforeEach
    public void setup() {
        productUtility = Mockito.mock(ProductUtility.class);
        when(productUtility.findProductName(anyInt())).thenReturn("");
        when(productUtility.isProductReady(anyInt())).thenReturn(false);
    }

    @Test
    public void findProductNames_productUtilityFindsNothing_returnsEmptyMap() {
        // GIVEN
        // All products have null names
        when(productUtility.findProductName(5)).thenReturn(null);
        when(productUtility.findProductName(6)).thenReturn(null);

        // WHEN
        // We get the product names
        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // THEN
        // Nothing is included in the map
        Map<Integer, String> itemNames = shipment.findProductNames();
        Assertions.assertEquals(0, itemNames.size(), "Map wasn't empty");
    }

    @Test
    public void isProductReady_productUtilityFindsNothing_returnsEmptyOptional() {
        // GIVEN
        // The item has a null readiness
        when(productUtility.isProductReady(5)).thenReturn(null);

        // WHEN
        // We check the shipment
        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5));

        // THEN
        // The result is an empty optional
        Optional<Boolean> itemReady = shipment.isProductReady(5);
        Assertions.assertEquals(false, itemReady.isPresent(), "Optional wasn't empty");
    }

    @Test
    public void findProductNames_productUtilityFindsNames_returnsPopulatedMap() {
        // GIVEN
        // The items have valid names
        when(productUtility.findProductName(5)).thenReturn("Outlander");
        when(productUtility.findProductName(6)).thenReturn("Tossed Salad");

        // WHEN
        // We check the shipment
        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // THEN
        // The map contains all the object names
        Map<Integer, String> itemNames = shipment.findProductNames();
        Assertions.assertAll("Checking to see if Map is populated correctly",
            () -> Assertions.assertEquals(2, itemNames.size(), "Map was wrong size"),
            () -> Assertions.assertEquals("Outlander", itemNames.get(5), "Map had wrong name"),
            () -> Assertions.assertEquals("Tossed Salad", itemNames.get(6), "Map had wrong name")
        );
    }

    @Test
    public void isProductReady_productIsReady_returnsPopulatedOptional() {
        // GIVEN
        // The items are ready to ship
        when(productUtility.isProductReady(6)).thenReturn(true);

        // WHEN
        // We check readiness
        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(6));

        // THEN
        // The map contains the item
        Optional<Boolean> itemReady = shipment.isProductReady(6);
        Assertions.assertAll("Checking to see if Optional is populated correctly",
            () -> Assertions.assertEquals(true, itemReady.isPresent(), "Optional should not have been empty"),
            () -> Assertions.assertEquals(true, itemReady.get(), "Item should have been ready to ship")
        );
    }

    @Test
    public void findProductNames_nullProductUtility_throwsIllegalArgumentException() {
        // GIVEN
        // The default setup

        // WHEN
        // We check shipment readiness
        ProductInventory shipment = new ProductInventory(null, Arrays.asList(5, 6));

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, String> itemNames = shipment.findProductNames();
        });
        Assertions.assertEquals("productUtility is null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }

    @Test
    public void findProductNames_nullProductIDs_throwsIllegalArgumentException() {
        // GIVEN
        // The default item setup

        // WHEN
        // We check a null item list
        ProductInventory shipment = new ProductInventory(productUtility, null);

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, String> itemNames = shipment.findProductNames();
        });
        Assertions.assertEquals("productID is null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }

    @Test
    public void isProductReady_wrongItemNumber_throwsIllegalArgumentException() {
        // GIVEN
        // The called method will NPE
        when(productUtility.isProductReady(null)).thenThrow(NullPointerException.class);

        // WHEN
        // We check the shipment readiness
        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Optional itemReady = shipment.isProductReady(null);
        });
        Assertions.assertEquals("The productID was null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }
}
