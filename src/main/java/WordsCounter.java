import utils.FileProcessor;
import java.util.*;
import java.util.stream.Collectors;

final class WordsCounter {

    private static final Set<String> OBSCENE_WORDS = new HashSet<>();
    private static final int MIN_WORD_LENGTH = 3;

    static {
        OBSCENE_WORDS.addAll(Arrays.asList("fuck", "cunt", "shit", "whore"));
    }

    private String rawTextData;
    private int totalWords;
    private List<String> excludedWords = new ArrayList<>();
    private SortedSet<Map.Entry<String, Integer>> sortedFreqs = new TreeSet<>(
            Comparator.comparing(Map.Entry<String, Integer>::getValue)
                    .reversed()
                    .thenComparing(Map.Entry::getKey));

    WordsCounter(final String textFileName) {
        init(textFileName);
    }

    int getTotalWords() {
        return totalWords;
    }

    String[] getFilteredOutWords() {
        return excludedWords.toArray(new String[0]);
    }

    int countFilteredOutWords() {
        return excludedWords.size();
    }

    List<Map.Entry<String, Integer>> getMostFrequentWords(final int n) {
        return sortedFreqs.stream().limit(n).collect(Collectors.toList());
    }

    void printMostFrequentWords(int n) {
        for (Map.Entry<String, Integer> entry : getMostFrequentWords(n)) {
            System.out.println(entry.getValue() + " times: " + entry.getKey());
        }
    }

    private void removeExtraCharacters() {
        rawTextData = rawTextData.replace(",", "").replace(".", "");
        rawTextData = rawTextData.replace("(", "").replace(")", "");
        rawTextData = rawTextData.replace("!", "").replace("  ", " ").toLowerCase();
    }

    private void init(final String textFileName) {
        rawTextData = new FileProcessor().retrieveFileContents(textFileName);
        removeExtraCharacters();
        String[] words = rawTextData.split(" ");
        countWords(words);
    }

    private void countWords(final String[] words) {
        Map<String, Integer> frequencies = new HashMap<>();
        if (null == words) {
            return;
        }
        for (String word : words) {
            if (word.length() < MIN_WORD_LENGTH || OBSCENE_WORDS.contains(word)) {
                excludedWords.add(word);
            } else if (frequencies.containsKey(word)) {
                frequencies.put(word, frequencies.get(word) + 1);
                totalWords++;
            } else {
                frequencies.put(word, 1);
                totalWords++;
            }
        }
        sortedFreqs.addAll(frequencies.entrySet());
    }
}
