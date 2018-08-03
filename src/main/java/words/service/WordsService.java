package words.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface WordsService {

    /**
     * A method to fetch the words.
     *
     * @return all words.
     */
    List<String> getAllWords();

    /**
     * A method to get the word by id.
     *
     * @param id words' unique identifier.
     * @return a map of two values - index and word that was searched for.
     */
    Map<Integer, String> findById(int id);

    /**
     * A method to update the word specifying its index.
     *
     * @param index words index - position.
     * @param word  the word.
     */
    void putWord(int index, String word);

    /**
     * A method to add the word to the end of the current words.
     *
     * @param word the word.
     */
    void postWord(String word);

    /**
     * A method to delete the word.
     *
     * @param index words' index.
     * @param word  the word.
     * @throws Exception throws exception
     */
    void deleteWord(int index, String word) throws Exception;

    /**
     * A method to find palindromes.
     *
     * @return a list of palindromes.
     */
    List<String> findPalindromes();

    /**
     * A method to fetch the indexes of a given word.
     *
     * @param value the word.
     * @return a list of indexes.
     */
    List<Integer> getIndexes(final String value);
}
