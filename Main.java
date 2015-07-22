/*
    Main.java
    v 1.0
    Author: Shreyas Jayanna
    Date: 7/22/15
*/

// Import statements
import java.io.*;
import java.util.logging.Logger;

/**
 * Class Main
 * This class contains the main method and the method to load the dictionary.
 */
public class Main {

    private static Logger log = Logger.getLogger(Dictionary.class.getName());

    /**
     * This method loads the dictionary from the words-list file.
     * @param dict      Dictionary object
     * @param filename  Words-list filename
     */
    public static void setupDictionary(Dictionary dict, String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String word;
            System.out.println("Loading dictionary... please wait!");
            while((word = br.readLine()) != null) {
                dict.addWord(word);
            }
            System.out.println("Dictionary is up & loaded! Let's play!!");
        } catch (FileNotFoundException e) {
            log.severe("Words file not found!");
        } catch (IOException e) {
            log.severe("Word read exception");
        }
    }

    /**
     * The main method loads the dictionary and invokes the game.
     * @param args  Command line arguments
     */
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        setupDictionary(dict, "H:\\words.txt");

        Ghost game = new Ghost(dict);
        game.play();
    }




}
