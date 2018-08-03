package words.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import words.service.WordsService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class WordsController {

    private static final Logger LOGGER = Logger.getLogger(WordsController.class.getName());

    private static final String WORDS_URI = "/words";
    private static final String WORD_URI = WORDS_URI + "/word";
    private static final String GET_PALINDROMES_URI = WORDS_URI + "/palindromes";

    @Autowired
    WordsService wordsService;


    @RequestMapping(value = WORDS_URI, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getWords() {
        List<String> words = wordsService.getAllWords();
        if (words.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @RequestMapping(value = WORD_URI, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<Integer>>> getWord(@RequestParam String word) {

        List<Integer> indexes = wordsService.getIndexes(word);

        if (indexes.isEmpty()) {
            LOGGER.info("The word provided has not been found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, List<Integer>> indexMap = new HashMap<>();
        indexMap.put(word, indexes);

        return new ResponseEntity<>(indexMap, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PALINDROMES_URI, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPalindromes() {
        List<String> words = wordsService.findPalindromes();
        if (words.isEmpty()) {
            LOGGER.info("No palindromes found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @RequestMapping(value = WORDS_URI, method = RequestMethod.PUT)
    public ResponseEntity<Map<Integer, String>> putWord(@RequestParam int index
            , @RequestParam String word) {

        Map<Integer, String> foundWord = wordsService.findById(index);
        if (StringUtils.isEmpty(foundWord)) {
            LOGGER.info("No word to update at the index");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wordsService.putWord(index, word);
        return new ResponseEntity<>(wordsService.findById(index), HttpStatus.OK);
    }

    @RequestMapping(value = WORDS_URI, method = RequestMethod.POST)
    public ResponseEntity<List<String>> postWord(@RequestParam String word) {
        wordsService.postWord(word);
        List<String> updatedWords = wordsService.getAllWords();
        return new ResponseEntity<>(updatedWords, HttpStatus.CREATED);
    }

    @RequestMapping(value = WORDS_URI, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteWord(@RequestParam int index
            , @RequestParam String word) {
        Map<Integer, String> foundWord = wordsService.findById(index);
        if (StringUtils.isEmpty(foundWord)) {
            LOGGER.info("The word provided has not been found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            wordsService.deleteWord(index, word);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}