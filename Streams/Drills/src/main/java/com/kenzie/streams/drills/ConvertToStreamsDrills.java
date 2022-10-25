package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Card;
import com.kenzie.streams.drills.resources.Dish;
import com.kenzie.streams.drills.resources.Rank;
import com.kenzie.streams.drills.resources.Suit;
import com.kenzie.streams.drills.resources.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Convert the following methods from using for loops to
 * using Streams.
 */
public class ConvertToStreamsDrills {

    /**
     * Calculates the square roots of a list of numbers.
     * @param numbers a list of numbers
     * @return a list of square roots of the numbers.
     */
    public static List<Double> returnSquareRoot(List<Integer> numbers) {
        List<Double> result = new ArrayList<>();
        for (int number : numbers) {
            result.add(Math.sqrt(number));
        }
        return result;
    }

    /**
     * Returns a list of ages of the provided Users.
     * @param users a list of Users.
     * @return a list of Users' ages.
     */
    public static List<Integer> getAgeFromUsers(List<User> users) {
        List<Integer> result = new ArrayList<>();
        for (User user : users) {
            result.add(user.getAge());
        }
        return result;
    }

    /**
     * Returns a list of distinct ages of the provided Users.
     * @param users a list of Users.
     * @return a distinct list of Users ages.
     */
    public static List<Integer> getDistinctAges(List<User> users) {
        List<Integer> result = new ArrayList<>();
        for (User user : users) {
            if (!result.contains(user.getAge())) {
                result.add(user.getAge());
            }
        }
        return result;
    }

    /**
     * Returns a sublist of the input list of Users, based on a provided limit.
     * @param users a list of Users.
     * @param limit the maximum size of the sublist to return.
     * @return a sublist of the input list of Users.
     */
    public static List<User> getLimitedUserList(List<User> users, int limit) {
        List<User> result = new ArrayList<>();
        for (int i = 0; i < limit && i < users.size(); i++) {
            result.add(users.get(i));
        }
        return result;
    }

    /**
     * Counts the users in the input list whose age is > 25.
     * @param users a list of Users.
     * @return the total count of Users whose age is > 25.
     */
    public static long countUsersOlderThan25(List<User> users) {
        int count = 0;
        for (User user : users) {
            if (user.getAge() > 25) {
                count++;
            }
        }
        return count;
    }

    /**
     * Searches the given list of Users for one with a particular name.
     *
     * Returns on the first instance of a User with the matching name.
     *
     * @param users a list of Users.
     * @param name the name to search for.
     * @return an Optional User whose name matches the input
     */
    public static Optional<User> findAny(List<User> users, String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Sorts the input list of Dishes by their calorie value, first removing
     * any that are above 400 calories.
     * @param menu a list of Dishes.
     * @return Dishes that are low in calories, sorted by number of calories.
     */
    public static List<String> sortDishesByCalories(List<Dish> menu) {
        List<Dish> lowCaloricDishes = new LinkedList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        Collections.sort(lowCaloricDishes, Comparator.comparingInt(Dish::getCalories));
        List<String> lowCaloricDishesNames = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesNames.add(dish.getName());
        }
        return lowCaloricDishesNames;
    }

    /**
     * Generate a new deck of cards.  Make sure you have a card of every suit and rank.
     * @return every card that should be in a deck
     */
    public static List<Card> newDeck() {
        List<Card> result = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                result.add(new Card(suit, rank));
            }
        }
        return result;
    }
}
