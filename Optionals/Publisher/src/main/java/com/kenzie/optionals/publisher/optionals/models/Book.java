package com.kenzie.optionals.publisher.optionals.models;

import java.util.*;

public class Book {
    private final String isbn;
    private final String title;
    private final List<Printing> printings;
    private final List<Integer> starRatings;

    public Book(String isbn, String title, List<Printing> printings, List<Integer> starRatings) {
        if (isbn == null) {
            throw new IllegalArgumentException("Book must have non-null ISBN!");
        }

        if (title == null) {
            throw new IllegalArgumentException("Book must have non-null title!");
        }

        if (printings == null) {
            throw new IllegalArgumentException("Book must have non-null printings list!");
        }

        if (starRatings == null) {
            throw new IllegalArgumentException("Book must have non-null star ratings list!");
        }

        this.isbn = isbn;
        this.title = title;
        this.printings = new ArrayList<>(printings);
        this.starRatings = new ArrayList<>(starRatings);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<Printing> getPrintings() {
        return new ArrayList<>(printings);
    }

    public List<Integer> getStarRatings() {
        return new ArrayList<>(starRatings);
    }

    /**
     * This method does complicated calculations to determine the book's star
     * rating.
     * It considers the number of reviews, backtracks associations between
     * reviewers and authors to identify shills, examines order history to find
     * verified reviewers, and uses a root-mean-square algorithm to calculate
     * a weighted star rating.
     *
     * This implementation, however, just averages a list of ratings.
     * @return An Optional of the book's weighted star rating, if any ratings
     *     have been left.
     */
    public Optional<Double> getWeightedRating() {
        Optional<IntSummaryStatistics> statistics = Optional.ofNullable(starRatings.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics());

        if (statistics.isPresent() && statistics.get().getCount() == 0) {
            return Optional.empty();
        } else {
            return statistics.map(IntSummaryStatistics::getAverage);
        }
    }

    /**
     * Returns the latest paperback printing of the book.
     * @return An Optional containing the latest paperback printing of the book,
     *     if any.
     */
    public Optional<Printing> getPaperback() {
        Printing latestPaperback = null;
        for(Printing printing : printings) {
            if (printing.getPrintingType() == PrintingType.PAPERBACK) {
                if(latestPaperback == null || latestPaperback.getPrintDate().before(printing.getPrintDate())) {
                    latestPaperback = printing;
                }
            }

        }
        return Optional.ofNullable(latestPaperback);
    }
}
