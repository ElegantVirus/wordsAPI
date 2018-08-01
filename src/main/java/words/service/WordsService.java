package words;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WordsService {
    private static final String FILE_PATH = "\\src\\main\\java\\words\\text.txt";
    private static List<String> words = new LinkedList<>();

    public WordsService() {
        setWords(FILE_PATH);
    }

    public List<String> getAllWords() {
        return words;
    }

    public Map<Integer, String> findById(int id) {
        return Collections.singletonMap(id, words.get(id));
    }

    static void setWords(final String filePath) {
        String currentDirectory = System.getProperty("user.dir");
        try {
            words = new LinkedList<>(Arrays.asList(
                    new Scanner(new File(currentDirectory + filePath)).useDelimiter("\\Z").next().split("[^\\w']+"))
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<String> findPalindromes() {
        List<String> palindromeList = new ArrayList<>();
        words.forEach(word -> {
            if (isPalindrome(word)) {
                palindromeList.add(word);
            }
        });
        return palindromeList;
    }

    private static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    private static List<Integer> getIndexes(String value) {
        List<Integer> indexes = new ArrayList<>();
        final int[] i = {0};
        words.forEach(word -> {
            if (word.equals(value))
                indexes.add(i[0]);
            i[0]++;
        });
        return indexes;
    }
}
