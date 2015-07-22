/*
    Ghost.java
    v 1.0
    Author: Shreyas Jayanna
    Date: 7/22/15
*/

// Import statements
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Ghost class
 * This class defines the game play
 */
public class Ghost {

    StringBuffer guessWord;
    int player;     // 0 - Human player; 1 - Computer
    Dictionary dict;
    ArrayList<String> possibleWords;

    /**
     * Constructor
     * @param dict  Dictionary object
     */
    Ghost(Dictionary dict) {
        guessWord = new StringBuffer();
        player = 0;
        this.dict = dict;
    }

    /**
     * This method defines starts the game and checks for a winner in every round.
     */
    public void play() {
        System.out.println("---When prompted, type in your letter and hit enter---");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean gameInProgress = true;
        while(gameInProgress) {
            chooseNextLetter(br);
            if(!doesWordExist(guessWord.toString()) || wordFound(guessWord.toString())) {
                System.out.println("Guessed word: " + guessWord.toString());
                System.out.println(!doesWordExist(guessWord.toString())?"Guessed word cannot be extended to a valid word.":
                    "Guessed word exists in the dictionary.");
                if(player == 0) {
                    System.out.println("I win! Better luck next time :D");
                } else {
                    System.out.println("You win! Congratulations!");
                }
                System.out.println("--- GAME OVER ---");
                gameInProgress = false;
            } else {
                player = ++player % 2;
            }
        }
    }

    /**
     * This method takes input from user for the next letter guess if the current player is human.
     * If the current player is computer, this method chooses the next letter as per the following guideline:
     *  - If the computer has more than one move that will guarantee it to win, it should play randomly among all its winning moves
     *  - If the computer thinks it will lose, it should play such to extend the game as long as possible
     *
     * @param br    BufferedReader object
     */
    private void chooseNextLetter(BufferedReader br) {
        if(player == 0) {
            // If player is human
            char c;
            try {
                System.out.println("Guessed word so far: " + (guessWord.length() > 0 ? guessWord.toString() : "<no guesses yet>"));
                System.out.print("Your choice: ");
                String input = br.readLine();
                while(input == null) {
                    input = br.readLine();
                }
                //System.out.println("here");
                c = input.charAt(0);
                if (guessWord.length() == 0) {
                    // If this is the first guess in the game, extract possible words from dictionary
                    // based on the letter guessed by the human player
                    possibleWords = new ArrayList<String>(dict.getWords(c));
                }
                guessWord.append(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(player == 1) {
            // If player is computer
            ArrayList<String> possibleWinningWords = new ArrayList<String>();
            for(String word : possibleWords) {
                possibleWinningWords.add(word.substring(guessWord.toString().length()));
            }

            // From current node, all words of even length are winning words
            ArrayList<String> listOfWords = new ArrayList<String>();
            for(String str : possibleWinningWords) {
                if(str.length() % 2 == 0) {
                    listOfWords.add(str);
                }
            }

            if(listOfWords.size() > 0) {
                // If there is a winning word, choose that word and continue the game
                Random random = new Random();
                int choice = random.nextInt(listOfWords.size());
                String choiceWord = listOfWords.get(choice);
                guessWord.append(choiceWord.charAt(0));
                System.out.println("I choose " + choiceWord.charAt(0));
            } else {
                // If there is no wining word, choose the longest possible word and play with it
                // Sort the next possible words based on their lengths
                Collections.sort(possibleWinningWords, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if(o1.length() < o2.length()) {
                            return 1;
                        }
                        return -1;
                    }
                });
                // Choose the longest word
                String word = possibleWinningWords.get(possibleWinningWords.size()-1);
                guessWord.append(word.charAt(0));
                System.out.println("I choose " + word.charAt(0));
            }
        }

    }

    /**
     * This method checks if at least one word exists which start with the passed prefix.
     * It returns true if at least one word exists which meets the criteria and false otherwise.
     * @param charSeq   The prefix
     * @return          True if at least one word exists, false otherwise
     */
    public boolean doesWordExist(String charSeq) {
        //System.out.println("charseq: " + charSeq);
        ArrayList<String> newPossibleWords = new ArrayList<String>();
        // If words start with the new prefix, add all such words to a new possible-words list.
        for(String word : possibleWords) {
            if(word.startsWith(charSeq)){
               // System.out.println(word);
                newPossibleWords.add(word);
            }
        }
        // If there are new possible words, initialize possible-words list to the new list.
        if(newPossibleWords.size() > 0) {
            possibleWords = newPossibleWords;
            return true;
        }
        return false;
    }

    /**
     * This method checks if an exact word is found in the dictionary.
     * @param charSeq   The word prefix
     * @return          True if exact match, false otherwise
     */
    public boolean wordFound(String charSeq) {
        for(String word : possibleWords) {
            if(word.equals(charSeq)) {
                return true;
            }
        }
        return false;
    }
}
