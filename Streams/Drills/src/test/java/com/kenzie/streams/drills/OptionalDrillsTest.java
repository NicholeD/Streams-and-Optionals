package com.kenzie.streams.drills;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kenzie.streams.drills.resources.Car;
import com.kenzie.streams.drills.resources.Dish;
import com.kenzie.streams.drills.resources.Insurance;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

public class OptionalDrillsTest {

    @Test
    public void nonNullMenu_nonNullMenu_returnsMenu() {
        Dish dish = Dish.builder().withName("I has a name").build();
        List<Dish> dishes = OptionalDrills.nonNullMenu(ImmutableList.of(dish));

        assertNotNull(dishes,
            String.format("Expected returnNullDishList() with dish list %s to return a non-null result.",
                ImmutableList.of(dish)));

        assertFalse(dishes.isEmpty(),
            String.format("Expected returnNullDishList() with dish list %s to return a non-empty result.",
                ImmutableList.of(dish)));

        assertTrue(dishes.contains(dish),
                String.format("Expected returnNullDishList() with dish list %s to contain dish %s.",
                    ImmutableList.of(dish),
                    dish));
    }

    @Test
    public void nonNullMenu_nullMenu_returnsEmptyMenu() {
        List<Dish> dishes = OptionalDrills.nonNullMenu(null);

        assertNotNull(dishes,
            "Expected returnNullDishList() with a null dish list to return a non-null result.");

        assertTrue(dishes.isEmpty(),
            "Expected returnNullDishList() with a null dish list to return a non-null result.");
    }

    @Test
    public void getDishName_withDish_returnsName() {
        Optional<String> optional = OptionalDrills.getDishName(Dish.builder().withName("I has a name").build());
        assertTrue(optional.isPresent());
        assertEquals("I has a name", optional.get());
    }

    @Test
    public void getDishName_noDish_returnsEmpty() {
        Optional<String> optional = OptionalDrills.getDishName(null);
        assertFalse(optional.isPresent());
    }

    @Test
    public void getExistingInsuranceName_withName_returnsName() {
        Optional<String> optional = OptionalDrills.getExistingInsuranceName(new Car(new Insurance("insurance")));
        assertTrue(optional.isPresent());
        assertEquals("insurance", optional.get());
    }

    @Test
    public void getExistingInsuranceName_noInsuranceName_returnsEmpty() {
        Optional<String> optional = OptionalDrills.getExistingInsuranceName(new Car(new Insurance()));
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuanceName_noInsuranceName_returnsEmpty() {
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(new Car(new Insurance()));
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuranceName_noCar_returnsEmpty() {
        Optional<Car> car = Optional.empty();
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(car);
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuranceName_withCar_returnsCheapestInsuranceName() {
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(Optional.of(new Car(new Insurance())));
        assertTrue(optional.isPresent());
        assertEquals("Amazon Insurance", optional.get());
    }
}
