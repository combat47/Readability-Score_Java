package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input = "";

        try {
            input = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        double sentenceCount = 0;
        double characterCount = 0;
        double syllableCount = 0;
        double polySyllableCount = 0;

        if (String.valueOf(input.charAt(input.length() - 1)).matches("[^.?!]")) {
            input += ".";
            characterCount--;
        }

        input = input.replaceAll("[AEIOUYaeiouy]{2}", "()");
        input = input.replaceAll("[eE]\\b", "^");

        String[] arr = input.split(" ");

        double wordCount = arr.length;

        for (String s : arr) {

            if (s.substring(s.length() - 1).matches("[.?!]")) {
                sentenceCount++;
            }

            double syllableFound = 0;
            for (char c : s.toCharArray()) {
                characterCount++;

                if (String.valueOf(c).matches("[aeiouyAEIOUY(]")) {
                    syllableFound++;
                }
            }
            syllableCount += syllableFound == 0 ? 1 : syllableFound;
            polySyllableCount += syllableFound > 2 ? 1 : 0;
        }

        System.out.println("Words: " + (int) wordCount);
        System.out.println("Sentences: " + (int) sentenceCount);
        System.out.println("Characters: " + (int) characterCount);
        System.out.println("Syllables: " + (int) syllableCount);
        System.out.println("Polysyllables: " + (int) polySyllableCount);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();

        switch (selection) {
            case "ARI" -> {
                System.out.println();
                automatedReadabilityIndex(characterCount, wordCount, sentenceCount);
            }
            case "FK" -> {
                System.out.println();
                fkReadabilityTest(wordCount, sentenceCount, syllableCount);
            }
            case "SMOG" -> {
                System.out.println();
                smogIndex(sentenceCount, polySyllableCount);
            }
            case "CL" -> {
                System.out.println();
                clIndex(wordCount, characterCount, sentenceCount);
            }
            case "all" -> {
                System.out.println();
                double ariScore = automatedReadabilityIndex(characterCount, wordCount, sentenceCount);
                double fkRTest = fkReadabilityTest(wordCount, sentenceCount, syllableCount);
                double smog = smogIndex(sentenceCount, polySyllableCount);
                double cl = clIndex(wordCount, characterCount, sentenceCount);
                double average = (ariScore + fkRTest + smog + cl) / 4.00;
                System.out.printf("\nThis text should be understood in average by %.2f-year-olds.", average);
            }
        }
    }

    static double getAge(double score) {

        double age = switch ((int) Math.ceil(score)) {
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 9;
            case 4 -> 10;
            case 5 -> 11;
            case 6 -> 12;
            case 7 -> 13;
            case 8 -> 14;
            case 9 -> 15;
            case 10 -> 16;
            case 11 -> 17;
            case 12 -> 18;
            case 13 -> 24;
            case 14 -> 25;
            default -> -1;
        };

        return age;
    }

    static double automatedReadabilityIndex(double totalCharacters, double totalWords, double totalSentences) {

        double score = 4.71 * (totalCharacters / totalWords) + 0.5 * (totalWords / totalSentences) - 21.43;

        double age = getAge(score);

        System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).\n", score, (int) age);

        return age;
    }

    static double fkReadabilityTest(double totalWords, double totalSentences, double totalSyllables) {

        double score = 0.39 * (totalWords / totalSentences) + 11.8 * (totalSyllables / totalWords) - 15.59;

        double age = getAge(score);

        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }

    static double smogIndex(double totalSentences, double totalPolysyllables) {

        double score = 1.043 * Math.sqrt(totalPolysyllables * 30 / totalSentences) + 3.1291;

        double age = getAge(score);

        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }

    static double clIndex(double totalWords, double totalCharacters, double totalSentences) {

        double L = totalCharacters / totalWords * 100;
        double S = totalSentences / totalWords * 100;

        double score = 0.0588 * L - 0.296 * S - 15.8;

        double age = getAge(score);

        System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }
}
