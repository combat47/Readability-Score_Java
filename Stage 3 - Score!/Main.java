package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input = null;

        try {
            input = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        double sentenceCount = 0;
        double wordCount = 0;
        double characterCount = 0;

        for (int i = 0; i < input.length(); i++) {

            if (String.valueOf(input.charAt(i)).matches("[\\S]")) {
                characterCount++;
            }

            if (String.valueOf(input.charAt(i)).matches(" ") && String.valueOf(input.charAt(i - 1)).matches("[^.!?]")) {
                wordCount++;
            } else if (String.valueOf(input.charAt(i)).matches("[.!?]")) {
                wordCount++;
                sentenceCount++;
            }
        }

        if (String.valueOf(input.charAt(input.length() - 1)).matches("[^.!?]")) {
            wordCount++;
            sentenceCount++;
        }

        System.out.println("Words: " + (int) wordCount);
        System.out.println("Sentences: " + (int) sentenceCount);
        System.out.println("Characters: " + (int) characterCount);

        double score = 4.71 * (characterCount / wordCount) + 0.5 * (wordCount / sentenceCount) - 21.43;
        System.out.printf("The score is: %.2f\n", score);

        System.out.println("This text should be understood by " + ageDecider((int) Math.ceil(score)) + "-year-olds.");
    }

    private static String ageDecider(int score) {

        String age = "";

        switch (score) {

            case 1:
                age = "5-6";
                break;

            case 2:
                age = "6-7";
                break;

            case 3:
                age = "7-9";
                break;

            case 4:
                age = "9-10";
                break;

            case 5:
                age = "10-11";
                break;

            case 6:
                age = "11-12";
                break;

            case 7:
                age = "12-13";
                break;

            case 8:
                age = "13-14";
                break;

            case 9:
                age = "14-15";
                break;

            case 10:
                age = "15-16";
                break;

            case 11:
                age = "16-17";
                break;

            case 12:
                age = "17-18";
                break;

            case 13:
                age = "18-24";
                break;

            case 14:
                age = "24+";
                break;
        }
        return age;
    }
}
