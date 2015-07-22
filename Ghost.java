import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Shreyas on 7/21/2015.
 */
public class Ghost {

    StringBuffer guessWord;
    int player;     // 0 - Human player; 1 - Computer
    Dictionary dict;
    ArrayList<String> possibleWords;

    Ghost(Dictionary dict) {
        guessWord = new StringBuffer();
        player = 0;
        this.dict = dict;
    }

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

    private void chooseNextLetter(BufferedReader br) {
        if(player == 0) {
            // If player is human
            char c;
            try {
                System.out.println("Guessed word: " + guessWord.toString());
                String input = br.readLine();
                //System.out.println("Entered " + input);
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
            System.out.println("Computer's turn..");
            ArrayList<String> possibleWinningWords = new ArrayList<String>();
            for(String word : possibleWords) {
                possibleWinningWords.add(word.substring(guessWord.toString().length()));
            }
            Collections.sort(possibleWinningWords);

            // Print out possible words
            //System.out.println(possibleWinningWords);

            int total = possibleWinningWords.size()-1;
            int index = 0;
            String word = possibleWinningWords.get(index);
            int size = word.length();
            while((size % 2 != 0) && (index < total)){
                word = possibleWinningWords.get(++index);
                size = word.length();
            }
            if((size > 0) && (size % 2 == 0)) {
                // If there is a winning word, choose that word and continue the game
                guessWord.append(word.charAt(0));
                //System.out.println("Computer chose " + word.charAt(0));
            } else {
                // If there is no wining word, choose the longest possible word and play with it
                word = possibleWinningWords.get(total);
                guessWord.append(word.charAt(0));
                //System.out.println("Computer chose2 " + word.charAt(0));
            }
        }

    }

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

    public boolean wordFound(String charSeq) {
        for(String word : possibleWords) {
            if(word.equals(charSeq)) {
                return true;
            }
        }
        return false;
    }
}
