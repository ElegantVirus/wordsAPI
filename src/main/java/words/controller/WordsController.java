package words;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class WordsController {

    private static final String GET_WORDS_URI = "/words";
    private static final String GET_WORD_URI = GET_WORDS_URI + "/{id}";
    private static final String GET_PALINDROMES_URI = "/palindromes";

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

    @RequestMapping(value = GET_WORD_URI, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, String>> getWord(@PathVariable("id") int id) {
        Map<Integer, String> word = wordsService.findById(id);
        if (word.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(word, HttpStatus.OK);
    }

//    @RequestMapping(value = GET_WORDS_URI + GET_PALINDROMES_URI, method = GET)
//    public String getPalidromes() {
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("palindromes", findPalindromes());
//        return jsonResponse.toString();
//    }

//    @RequestMapping(method = PUT)
//    public String putWord(@RequestBody String json) throws JSONException {
//        JSONObject jsonObj = new JSONObject(json);
//        String word = (String) jsonObj.get("word");
//        int index = (int) jsonObj.get("index");
//        words.set(index, word);
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("words", words);
//        return jsonResponse.toString();
//    }
//
//    @RequestMapping(method = POST)
//    public String postWord(@RequestBody String json) throws JSONException {
//        JSONObject jsonObj = new JSONObject(json);
//        String word = (String) jsonObj.get("word");
//        words.add(word);
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("words", words);
//        return jsonResponse.toString();
//    }
//
//    @RequestMapping(method = DELETE)
//    public String deleteWord(@RequestBody String json) throws JSONException {
//        JSONObject jsonObj = new JSONObject(json);
//        String word = (String) jsonObj.get("word");
//        int index = (int) jsonObj.get("index");
//        if (getIndexes(word).contains(index)) {
//            words.remove(index);
//            JSONObject jsonResponse = new JSONObject();
//            jsonResponse.put("words", words);
//            return jsonResponse.toString();
//        } else return "No such word at the index";
//    }

}