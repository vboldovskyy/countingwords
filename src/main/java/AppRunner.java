import java.util.Arrays;
import java.util.Scanner;

class AppRunner {
    private static final int DEFAULT_MOST_FREQUENT_ENTRIES = 5;
    private WordsCounter wordsCounter = new WordsCounter("data.txt");

    void run() {
        System.out.println("1. Total words in the song: " + wordsCounter.getTotalWords());
        System.out.println("2. Excluded were " + wordsCounter.countFilteredOutWords() + " words. Here they are:");
        System.out.println(Arrays.toString(wordsCounter.getFilteredOutWords()));
        System.out.println("3. Enter how many most frequent words you would like to see?");
        int n = DEFAULT_MOST_FREQUENT_ENTRIES;
        try {
            n = new Integer(new Scanner(System.in).nextLine());
        } catch (Exception e) {
            System.out.println("Your number has not been recognized. We will use 5 instead");
        }
        wordsCounter.printMostFrequentWords(n);
    }

 
}
