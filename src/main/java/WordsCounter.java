import utils.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

 final class WordsCounter {

    private static final Set<String> OBSCENE_WORDS = new HashSet<>();
    private static final int MIN_WORD_LENGTH = 3;

    static {
        OBSCENE_WORDS.addAll(Arrays.asList("fuck", "cunt", "shit", "whore"));
    }

    private String rawData;
    private int totalWords;
    private Map<String, Integer> frequencies = new HashMap<>();
    private List<String> excludedWords = new ArrayList<>();
    private SortedSet<Map.Entry<String, Integer>> sortedFreqs = new TreeSet<>(
            Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey));

    WordsCounter(final String data) {
        rawData = new FileUtil().retrieveFileContents(data);
        removeExtraCharacters();
        String[] words = rawData.split(" ");

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

    int getTotalWords() {
        return totalWords;
    }

    String[] getFilteredOutWords() {
        return excludedWords.toArray(new String[excludedWords.size()]);
    }

    int countFilteredOutWords() {
        return excludedWords.size();
    }

    List<Map.Entry<String, Integer>> getMostFrequentWords(final int n) {
        return sortedFreqs.stream().limit(n).collect(Collectors.toList());
    }

    private void removeExtraCharacters() {
        rawData = rawData.replace(",", "").replace(".", "");
        rawData = rawData.replace("(", "").replace(")", "");
        rawData = rawData.replace("!", "").replace("  ", " ").toLowerCase();
    }
}
