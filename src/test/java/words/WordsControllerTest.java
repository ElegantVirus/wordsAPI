package words;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WordsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String WORDS_URI = "/words";
    private static final String WORD_URI = WORDS_URI + "/word";
    private static final String GET_PALINDROMES_URI = WORDS_URI + "/palindromes";

    @Test
    public void shouldReturnAllWords() throws Exception {
        this.mockMvc.perform(get(WORDS_URI)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(notNullValue()));
    }

    @Test
    public void shouldReturnOneWord() throws Exception {
        this.mockMvc.perform(get(WORD_URI + "?word=racecar"))
                .andDo(print()).andExpect(status()
                .isOk())
                .andExpect(content().string(containsString("racecar")));
    }

    @Test
    public void shouldReturnAllPalindromes() throws Exception {
        this.mockMvc.perform(get(GET_PALINDROMES_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("racecar")));

    }

    @Test
    public void shouldUpdateWord() throws Exception {
        this.mockMvc.perform(put(WORDS_URI + "?index=1&word=palindrome1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("palindrome1")));
    }

    @Test
    public void shouldDeleteWord() throws Exception {
        this.mockMvc.perform(delete(WORDS_URI + "?index=0&word=A"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldAddWord() throws Exception {
        this.mockMvc.perform(post(WORDS_URI + "?word=lastWord"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("lastWord")));
    }
}