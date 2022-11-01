package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Trader;
import com.kenzie.streams.drills.resources.Transaction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Use the provided Trader and Transaction data to implement the class's methods.
 *
 * Do NOT modify the Trader or Transaction data.
 */
public class TransactionQuestions {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );

    /**
     * Find all transactions in the year 2011 and sort them by value (small to large).
     * @return a list of all transactions that occurred in 2011
     */
    public List<Transaction> transactions2011() {
        if (transactions != null) {
            return transactions.stream()
                    .filter(i -> i.getYear() == 2011)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /**
     * What are all the unique cities where the traders work?
     * @return A list of all unique cities traders work in
     */
    public List<String> uniqueCities() {
        if (transactions != null) {
            return transactions.stream()
                    .map(i -> i.getTrader().getCity())
                    .distinct()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Find all traders from Cambridge and sort them by name alphabetically.
     * @return a list of all traders based in cambridge
     */
    public List<Trader> cambridgeTraders() {
            return transactions.stream()
                    .filter(i -> i.getTrader().getCity() == "Cambridge")
                    .map(i -> i.getTrader())
                    .distinct()
                    .sorted(Comparator.comparing(Trader::getName))
                    .collect(Collectors.toList());
    }

    /**
     * Return a single string of all traders' names sorted alphabetically.
     *
     * There should be no characters in between each name.
     * @return a concatenated string of all trader names
     */
    public String traderNames() {
        if (transactions != null) {
            return transactions.stream()
                    .map(i -> i.getTrader().getName())
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining());
        }
        return "";
    }

    /**
     * Are any traders based in Milan?
     * @return true, if any traders are Milan based
     */
    public boolean isMilanBased() {
        if (transactions != null) {
            List<Trader> milanTrader = transactions.stream()
                    .filter(i -> i.getTrader().getCity() == "Milan")
                    .map(i -> i.getTrader())
                    .collect(Collectors.toList());
            if (milanTrader.isEmpty()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Print all transactions' values from the traders living in Cambridge.
     */
    public void printCambridgeTransactions() {
        Optional.ofNullable(transactions).orElse(Collections.emptyList())
                .stream()
                .filter(i -> i.getTrader().getCity() == "Cambridge")
                .forEach(System.out::println);
    }

    /**
     * What's the highest value of all the transactions?
     * @return An optional with the highest value of a trade, if a trade occurred.
     */
    public Optional<Integer> highestValueTrade() {
        List<Integer> listOfValues = Optional.ofNullable(transactions).orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .map(i -> i.getValue())
                .collect(Collectors.toList());

        return Optional.ofNullable(listOfValues.get(listOfValues.size() - 1));
    }

    /**
     * Find the transaction with the smallest value.
     * @return An optional with the transaction with the smallest value, if a transaction exists.
     */
    public Optional<Transaction> smallestTransaction() {
        List<Transaction> listOfTransactions = Optional.ofNullable(transactions).orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        return Optional.ofNullable(listOfTransactions.get(0));
    }
}
