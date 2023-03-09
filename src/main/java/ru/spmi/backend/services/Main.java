package ru.spmi.backend.services;

import java.util.*;

public class Main {

    public static ArrayList<String> first = new ArrayList<>();
    public static ArrayList<String> second = new ArrayList<>();
    public static HashMap<String, String> outMap = new HashMap<>();

    public static void main(String[] args) {
        readData();
        String maxSimilarWord;

        for (int i = 0; i < first.size(); i++) {
            maxSimilarWord = new String(findSimilar(first.get(i)));
            if (maxSimilarWord.equals("")) continue;
            outMap.put(first.get(i), maxSimilarWord);
            second.remove(findSimilar(first.get(i)));
            first.remove(i);
            i--;
        }

        for (int i = 0; i < second.size(); i++) {
            maxSimilarWord = new String(findSimilar(second.get(i)));
            if (maxSimilarWord.equals("")) continue;
            outMap.put(second.get(i), maxSimilarWord);
            first.remove(findSimilar(second.get(i)));
            second.remove(i);
            i--;
        }

        ArrayList<String> aloneStrings = new ArrayList<>();
        aloneStrings.addAll(first);
        aloneStrings.addAll(second);
        aloneStrings.stream().forEach(x -> outMap.put(x, "?"));

        for (String key: outMap.keySet()) {
            System.out.println(key + ":" + outMap.get(key));
        }
    }

    public static void readData() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) first.add(scanner.nextLine());
            int m = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < m; i++) second.add(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String findSimilar(String word) {
        int maxMatch = 0;
        String maxSimilarWord = "";
        var wordItems = word.split(" ");
        int curMatch = 0;
        for (int i = 0; i < wordItems.length; i++) {
            for (String candidate : second) {
                curMatch += candidate.split(wordItems[i]).length - 1;
                if (curMatch > maxMatch) {
                    maxMatch = curMatch;
                    maxSimilarWord = candidate;
                }
            }
        }
        return maxSimilarWord;
    }
}
