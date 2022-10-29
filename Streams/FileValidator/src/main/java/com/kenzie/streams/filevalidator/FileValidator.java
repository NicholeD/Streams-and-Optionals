package com.kenzie.streams.filevalidator;

import com.kenzie.streams.filevalidator.resources.ImporterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileValidator {
    private List<String> sourceFileNames;

    /**
     * Constructor for FileValidator.
     */
    public FileValidator() {
        ImporterManager importManager = new ImporterManager();
        sourceFileNames = importManager.importFiles();
        validateFiles();
        validateFilesStream();
    }

    /**
     * Iterates through all file names in sourceFileNames and validates them.
     * @return The validated list of file names.
     */
    public List<String> validateFiles() {
        List<String> validFileNames = sourceFileNames;

        validFileNames = removeDraftFiles(validFileNames);
        validFileNames = removeHiddenFiles(validFileNames);
        validFileNames = makeLowerCase(validFileNames);
        sortList(validFileNames);

        return validFileNames;
    }

    /**
     * Makes all elements in a list lowercase.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> makeLowerCase(List<String> list) {
        List<String> lowerList = new ArrayList<>();
        for (String s : list) {
            lowerList.add(s.toLowerCase());
        }
        return lowerList;
    }

    /**
     * Filters out all draft files in a list.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> removeDraftFiles(List<String> list) {
        List<String> filteredList = new ArrayList<>();
        for (String s : list) {
            if (!s.contains("_Draft")) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    /**
     * Filters out all hidden files in a list.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> removeHiddenFiles(List<String> list) {
        List<String> filteredList = new ArrayList<>();
        for (String s : list) {
            if (!s.startsWith(".")) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    /**
     * Sorts all elements in a list in the natural order.
     * @param list Source list.
     */
    public void sortList(List<String> list) {
        Collections.sort(list);
    }

    /**
     * Uses a Stream to process all file names in sourceFileNames and validates them.
     * PARTICIPANTS: Complete this method.
     * @return The validated list of file names.
     */
    public List<String> validateFilesStream() {
        Stream<String> fileNameStream = createStream(sourceFileNames);

        return  fileNameStream.map(String::toLowerCase)
                .filter(i -> !(i.contains("_draft")))
                .filter (i -> !(i.startsWith(".")))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Create a stream from the given List.
     * PARTICIPANTS: Complete this method.
     * @param files Source List.
     * @return Created stream.
     */
    public Stream<String> createStream(List<String> files) {
        return files.stream();
    }

    /**
     * Makes all elements in stream lowercase.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> makeLowerCaseStream(Stream<String> stream) {
        return stream.map(String::toLowerCase);
    }

    /**
     * Filters out all files in a stream with "draft" in their name.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> removeDraftFilesStream(Stream<String> stream) {
        return stream.filter(i -> !(i.contains("_draft")));
    }

    /**
     * Filters out all hidden files in a stream. Hidden files are files that start with a period.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> removeHiddenFilesStream(Stream<String> stream) {
        return stream.filter (i -> !i.startsWith("."));
    }

    /**
     * Sorts all elements in a stream in the natural order.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> sortListStream(Stream<String> stream) {
        return stream.sorted();
    }

    /**
     * Collects the stream as a List.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return List of results.
     */
    public List<String> collectStreamResults(Stream<String> stream) {
        return stream.collect(Collectors.toList());
    }
}
