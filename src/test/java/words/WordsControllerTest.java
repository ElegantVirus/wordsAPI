package words;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WordsController.class)
public class WordsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String FILE_PATH = "\\src\\main\\java\\words\\text.txt";

    @Before
    public void setup() {
        WordsController.setWords(FILE_PATH);

    }

    @Test
    public void testGetWords() throws Exception {
        this.mockMvc.perform(get("/words")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("words")));
    }

    @Test
    public void testGetWord() throws Exception {
        this.mockMvc.perform(get("/words/word?word=number")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("number")));
    }

    @Test
    public void testGetPalindromes() throws Exception {
        this.mockMvc.perform(get("/words/palindromes")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("madam")));
    }

    @Test
    public void testPutWord() throws Exception {
        JSONObject json = new JSONObject();
        json.put("word", "random");
        json.put("index", 1);
        this.mockMvc.perform(put("/")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("random")));
    }

    @Test
    public void testPostWord() throws Exception {
        JSONObject json = new JSONObject();
        json.put("word", "random");
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("random")));
    }

    @Test
    public void testDeleteWord() throws Exception {
        JSONObject json = new JSONObject();
        json.put("word", "number");
        json.put("index", 5);
        this.mockMvc.perform(delete("/")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteWordMismatch() throws Exception {
        JSONObject json = new JSONObject();
        json.put("word", "nununu");
        json.put("index", 5);
        this.mockMvc.perform(delete("/")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andDo(print())
                .andExpect(content().string(containsString("No such word at the index")));
    }
}