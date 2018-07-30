package words;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class WordsController {

    private static List<String> words = new LinkedList<>();
    private static final String GET_WORDS_URI = "/words";
    private static final String GET_WORD_URI = "/word";
    private static final String GET_PALINDROMES_URI = "/palindromes";

    static void setWords(final String filePath) {
        String currentDirectory = System.getProperty("user.dir");
        try {
            words = new LinkedList<>(Arrays.asList(
                    new Scanner(new File(currentDirectory+filePath)).useDelimiter("\\Z").next().split("[^\\w']+"))
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

    @RequestMapping(value = GET_WORDS_URI, method = GET)
    public String getWords() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("words", words);
        return jsonResponse.toString();
    }

    @RequestMapping(value = GET_WORDS_URI + GET_WORD_URI, method = GET)
    public String getWord(@RequestParam(value = "word") String value) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("word", value);
        jsonResponse.put("indexes", getIndexes(value));
        return jsonResponse.toString();
    }

    @RequestMapping(value = GET_WORDS_URI + GET_PALINDROMES_URI, method = GET)
    public String getPalidromes() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("palindromes", findPalindromes());
        return jsonResponse.toString();
    }

    @RequestMapping(method = PUT)
    public String putWord(@RequestBody String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        String word = (String) jsonObj.get("word");
        int index = (int) jsonObj.get("index");
        words.set(index, word);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("words", words);
        return jsonResponse.toString();
    }

    @RequestMapping(method = POST)
    public String postWord(@RequestBody String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        String word = (String) jsonObj.get("word");
        words.add(word);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("words", words);
        return jsonResponse.toString();
    }

    @RequestMapping(method = DELETE)
    public String deleteWord(@RequestBody String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        String word = (String) jsonObj.get("word");
        int index = (int) jsonObj.get("index");
        if (getIndexes(word).contains(index)) {
            words.remove(index);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("words", words);
            return jsonResponse.toString();
        } else return "No such word at the index";
    }

}