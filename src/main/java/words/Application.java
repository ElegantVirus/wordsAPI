package words;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    private static final String FILE_PATH = "\\src\\main\\java\\words\\text.txt";

    public static void main(String[] args) {
        WordsController.setWords(FILE_PATH);
        SpringApplication.run(Application.class, args);
    }

}