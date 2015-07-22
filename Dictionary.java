import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shreyas on 7/21/2015.
 */
public class Dictionary {
    HashMap<Character,Node> nodeMap;
    HashMap<Character,ArrayList<String>> words;

    Dictionary() {
        this.nodeMap = new HashMap<Character, Node>();
        this.words = new HashMap<Character, ArrayList<String>>();
    }

    public void addWord(String word) {
        if(this.nodeMap.containsKey(word.charAt(0))) {
            // If a graph already exists for a character, insert new word's characters as nodes
            this.nodeMap.get(word.charAt(0)).addWord(word,1);
        } else {
            // If a graph doesn't exist for a character, create a new node with first character
            // of the word and insert the remaining characters to the graph
            Node node = new Node(word.charAt(0));
            node.addWord(word,1);                     // Insert remaining chars into graph
            this.nodeMap.put(word.charAt(0),node);
        }

        // Store the words in a HashMap where key = first_character and value = list of words with that first char
        if(this.words.containsKey(word.charAt(0))) {
            this.words.get(word.charAt(0)).add(word);
        } else {
            ArrayList<String> wordList = new ArrayList<String>();
            wordList.add(word);
            this.words.put(word.charAt(0), wordList);
        }
    }

    public HashMap<Character,Node> getNodeMap() {
        return this.nodeMap;
    }

    public ArrayList<String> getWords(char c) {
        return this.words.get(c);
    }
}
