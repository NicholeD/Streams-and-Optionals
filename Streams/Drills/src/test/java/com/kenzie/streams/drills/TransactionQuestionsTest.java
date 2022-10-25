package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Trader;
import com.kenzie.streams.drills.resources.Transaction;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionQuestionsTest {
    private static final TransactionQuestions TRANSACTIONS = new TransactionQuestions();

    // Must match the values in TransactionQuestions
    private static final Trader RAOUL = new Trader("Raoul", "Cambridge");
    private static final Trader ALAN = new Trader("Alan","Cambridge");
    private static final Trader BRIAN = new Trader("Brian","Cambridge");
    private static final Transaction BRIAN_2011_300 = new Transaction(BRIAN, 2011, 300);
    private static final Transaction RAOUL_2012_1000 = new Transaction(RAOUL, 2012, 1000);
    private static final Transaction RAOUL_2011_400 = new Transaction(RAOUL, 2011, 400);

    @Test
    public void transactions2011_withTransactions_returnsTransactionsSortedAscending() {
        List<Transaction> transactions2011 = TRANSACTIONS.transactions2011();
        assertEquals(2, transactions2011.size(), "Expected transactions2011 to return " +
            "2 transactions for 2011.");
        assertEquals(BRIAN_2011_300, transactions2011.get(0),
            "Expected the first transaction in 2011 to be the lowest of the year.");
        assertEquals(RAOUL_2011_400, transactions2011.get(1),
            "Expected the second transaction in 2011 to be the highest of the year.");
    }

    @Test
    public void uniqueCities_multipleCities_returnsUniqueCities() {
        List<String> uniqueCities = TRANSACTIONS.uniqueCities();
        assertEquals(uniqueCities.size(), 2, "Expected 2 unique cities.");
        assertTrue(uniqueCities.contains("Cambridge"), "Expected Cambridge to be a unique city.");
        assertTrue(uniqueCities.contains("Milan"), "Expected Milan to be a unique city.");
    }

    @Test
    public void cambridgeTraders_withCambridgeTraders_returnsCambridgeTradersSortedAlphabetically() {
        List<Trader> cambridgeTraders = TRANSACTIONS.cambridgeTraders();
        assertEquals(3, cambridgeTraders.size(), "Expected cambridgeTraders to return " +
            "3 Cambridge-based traders.");
        assertEquals(ALAN, cambridgeTraders.get(0));
        assertEquals(BRIAN, cambridgeTraders.get(1));
        assertEquals(RAOUL, cambridgeTraders.get(2));
    }

    @Test
    public void traderNames_multipleTraders_concatenatesNamesSortedAlphabetically() {
        String traderNames = TRANSACTIONS.traderNames();
        assertEquals("AlanBrianMarioRaoul", traderNames);
    }

    @Test
    public void isMilanBased_containsMilanBasedTraders_returnsTrue() {
        assertTrue(TRANSACTIONS.isMilanBased());
    }

    @Test
    public void highestValueTrade_multipleTransactions_returnsHighest() {
        assertTrue(TRANSACTIONS.highestValueTrade().isPresent(), "Expected a non-empty " +
            "Optional of the highest value trade.");
        assertEquals(RAOUL_2012_1000.getValue(), TRANSACTIONS.highestValueTrade().get());
    }

    @Test
    public void smallestTransaction_multipleTransactions_returnsLowest() {
        assertTrue(TRANSACTIONS.smallestTransaction().isPresent(), "Expected a non-empty " +
            "Optional of the smallest value trade.");
        assertEquals(BRIAN_2011_300, TRANSACTIONS.smallestTransaction().get());
    }
}
