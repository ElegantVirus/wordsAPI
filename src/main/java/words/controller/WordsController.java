package words.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import words.service.WordsService;


import java.util.List;
import java.util.Map;

@RestController
public class WordsController {

    private static final String GET_WORDS_URI = "/words";
    private static final String GET_PALINDROMES_URI = GET_WORDS_URI + "/palindromes";

    @Autowired
    WordsService wordsService;


    @RequestMapping(value = GET_WORDS_URI, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getWords() {
        List<String> words = wordsService.getAllWords();
        if (words.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @RequestMapping(value = GET_WORDS_URI + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, String>> getWord(@PathVariable("id") int id) {
        Map<Integer, String> word = wordsService.findById(id);
        if (word.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(word, HttpStatus.OK);
    }

    @RequestMapping(value = GET_WORDS_URI + GET_PALINDROMES_URI, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPalindromes() {
        List<String> words = wordsService.getPalindromes();
        if (words.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Map<Integer, String>> putWord(@RequestParam int index
            , @RequestParam String word) {

        Map<Integer, String> foundWord = wordsService.findById(index);
        if (foundWord.get(index).isEmpty()) {
//Log - no such word
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wordsService.putWord(index, word);
        return new ResponseEntity<>(wordsService.findById(index), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<String>> postWord(@RequestParam String word) {
        wordsService.postWord(word);
        List<String> updatedWords = wordsService.getAllWords();
        return new ResponseEntity<>(updatedWords, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Map<Integer, String>> deleteWord(@RequestParam int index
            , @RequestParam String word) {
        Map<Integer, String> foundWord = wordsService.findById(index);
        if (foundWord.get(index).isEmpty()) {
//Log - no such word
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wordsService.deleteWord(index);
        return new ResponseEntity<>(wordsService.findById(index), HttpStatus.OK);
    }

}