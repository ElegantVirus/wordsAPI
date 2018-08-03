package words.service.impl;

import org.springframework.stereotype.Service;
import words.service.WordsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class WordsServiceImpl implements WordsService {
    private static final Logger LOGGER = Logger.getLogger(WordsService.class.getName());

    private static final String FILE_PATH = "\\src\\main\\java\\words\\text.txt";
    private static List<String> words = new LinkedList<>();

    public WordsServiceImpl() {
        setWords(FILE_PATH);
    }

    public List<String> getAllWords() {
        return words;
    }

    public Map<Integer, String> findById(final int id) {
        String word;
        LOGGER.info("Trying to find resource by its id");

        try {
            word = words.get(id);
        } catch (Exception e) {
            LOGGER.info("Wrong index provided");
            return null;
        }
        return Collections.singletonMap(id, word);
    }

    public void putWord(final int index, final String word) {
        LOGGER.info("Updating words");
        words.add(index, word);
    }

    public void postWord(final String word) {
        LOGGER.info("Adding word to the list");
        words.add(word);
    }

    public void deleteWord(final int index, final String name) throws IllegalAccessException {
        LOGGER.info("Trying to delete the word");
        if (words.get(index).equalsIgnoreCase(name)) {
            LOGGER.info("Word is deleted");
            words.remove(index);
        } else {
            throw new IllegalAccessException("Word at the index does not match the argument");
        }

    }

    private static void setWords(final String filePath) {
        LOGGER.info("Initialising the \"database\"");

        String currentDirectory = System.getProperty("user.dir");
        try {
            words = new LinkedList<>(Arrays.asList(
                    new Scanner(new File(currentDirectory + filePath)).useDelimiter("\\Z").next().split("[^\\w']+"))
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> findPalindromes() {
        LOGGER.info("Palindrome search is executed");

        return words.stream()
                .filter(this::isPalindrome)
                .collect(Collectors.toList());
    }

    private boolean isPalindrome(final String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    public List<Integer> getIndexes(final String value) {
        LOGGER.info("Trying to fetch the indexes of the given word");

        return IntStream.range(0, words.size())
                .filter(i -> words.get(i).equalsIgnoreCase(value))
                .boxed()
                .collect(Collectors.toList());
    }
}
