import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Shreyas on 7/21/2015.
 */
public class Main {

    private static Logger log = Logger.getLogger(Dictionary.class.getName());

    public static void setupDictionary(Dictionary dict, String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String word;
            while((word = br.readLine()) != null) {
                dict.addWord(word);
            }
        } catch (FileNotFoundException e) {
            log.severe("Words file not found!");
        } catch (IOException e) {
            log.severe("Word read exception");
        }
    }

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        setupDictionary(dict, "words.txt");
    }


}
