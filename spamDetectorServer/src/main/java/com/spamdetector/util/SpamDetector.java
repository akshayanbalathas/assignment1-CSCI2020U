package com.spamdetector.util;

import com.spamdetector.domain.TestFile;

import java.io.*;
import java.util.*;


/**
 * TODO: This class will be implemented by you
 * You may create more methods to help you organize you strategy and make you code more readable
 */
public class SpamDetector {

//    public List<TestFile> trainAndTest(File mainDirectory) {
////        TODO: main method of loading the directories and files, training and testing the model
//
//        return new ArrayList<TestFile>();
//    }

    private Map<String, Integer> trainHamFreq;
    private Map<String, Integer> trainSpamFreq;

    // Probability map for each word
    private Map<String, Double> probabilities;

    public SpamDetector() {
        trainHamFreq = new HashMap<>();
        trainSpamFreq = new HashMap<>();
        probabilities = new TreeMap<>();
    }

    // Train the spam detector with ham and spam files
    public void train(String hamFolder, String spamFolder) throws IOException {

        // Read all ham files and update frequency map
        File folder = new File(hamFolder);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        // Ignore case
                        word = word.toLowerCase();
                        // Ignore words with less than 3 characters
                        if (word.length() < 3) {
                            continue;
                        }
                        // Update frequency map
                        int count = trainHamFreq.getOrDefault(word, 0);
                        trainHamFreq.put(word, count + 1);
                    }
                }
                reader.close();
            }
        }

        // Read all spam files and update frequency map
        folder = new File(spamFolder);
        listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        // Ignore case
                        word = word.toLowerCase();
                        // Ignore words with less than 3 characters
                        if (word.length() < 3) {
                            continue;
                        }
                        // Update frequency map
                        int count = trainSpamFreq.getOrDefault(word, 0);
                        trainSpamFreq.put(word, count + 1);
                    }
                }
                reader.close();
            }
        }

        // Compute probabilities for each word
        for (String word : trainHamFreq.keySet()) {
            int hamCount = trainHamFreq.get(word);
            int spamCount = trainSpamFreq.getOrDefault(word, 0);
            double prob = (double) spamCount / (spamCount + hamCount);
            probabilities.put(word, prob);
        }

        for (String word : trainSpamFreq.keySet()) {
            if (probabilities.containsKey(word)) {
                continue;
            }
            int hamCount = trainHamFreq.getOrDefault(word, 0);
            int spamCount = trainSpamFreq.get(word);
            double prob = (double) spamCount / (spamCount + hamCount);
            probabilities.put(word, prob);
        }
    }

    // Test the spam detector with ham and spam files
    public List<TestFile> test(String hamFolder, String spamFolder) throws IOException {
        List<TestFile> testFiles = new ArrayList<>();

        // Read all ham files and classify as ham
        File folder = new File(hamFolder);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                TestFile testFile = classify(file, false);
                testFiles.add(testFile);
            }
        }

        // Read all spam files and classify as spam
        folder = new File(spamFolder);

}

