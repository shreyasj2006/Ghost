import java.io.*;
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
            int count = 0;
            System.out.println("Loading dictionary... please wait!");
            while((word = br.readLine()) != null) {
                dict.addWord(word);
                ++count;
            }
            System.out.println("Dictionary is up & loaded! Let's play!!");

        } catch (FileNotFoundException e) {
            log.severe("Words file not found!");
        } catch (IOException e) {
            log.severe("Word read exception");
        }
    }

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        setupDictionary(dict, "words.txt");

        Ghost game = new Ghost(dict);
        game.play();
    }




}
