/*
    Node.java
    v 1.0
    Author: Shreyas Jayanna
    Date: 7/22/15
*/

// Import statement
import java.util.HashMap;

/**
 * Class Node
 * This class defines a node to store a letter and its neighboring nodes
 */
public class Node {
    char _char;
    HashMap<Character,Node> nextNodes;
    boolean isLeaf;

    /**
     * Constructor
     * @param charValue The letter to be stored
     */
    Node(char charValue) {
        nextNodes = new HashMap<Character, Node>();
        _char = charValue;
        isLeaf = false;
    }

    /**
     * This method returns the letter stored in the Node
     * @return  Letter stored in the node
     */
    public char getChar() {
        return this._char;
    }

    /**
     * This method adds a word rooted at the current node. It does not store words which have a valid word as a substring
     * @param word      The word to be stored
     * @param index     The index of the current node's letter in the word
     */
    public void addWord(String word, int index) {
        // If a substring of this word is already a valid word in the dictionary, return
        if(index >= word.length()) {
            isLeaf = true;
            return;
        }

        // Calculate the index from which the word's letters need to be processed
        HashMap<Character, Node> nodesMap = this.nextNodes;
        while (true) {
            if ((index == word.length()) || (!nodesMap.containsKey(word.charAt(index)))) {
                isLeaf = true;
                break;
            } else {
                nodesMap = nodesMap.get(word.charAt(index)).getNextNodes();
                ++index;
            }
        }

        // Store the characters in the nodes
        for (int i = index; i < word.length(); ++i) {
            if(!this.isLeaf) {
                Node node = new Node(word.charAt(i));
                this.nextNodes.put(word.charAt(i), node);
                if (index + 1 != word.length())
                    node.addWord(word, ++index);
            }
        }
    }

    /**
     * This method returns the connected nodes hashmap
     * @return  Hashmap containing next nodes
     */
    public HashMap<Character,Node> getNextNodes() {
        return this.nextNodes;
    }

    /**
     * This method returns the number of nodes in the subtrees of this node
     * @return  Number of subtree nodes
     */
    public int countNextNodes() {
        int count = 0;
        for(Node node: this.nextNodes.values()) {
            count += node.countNextNodes();
        }
        return count;
    }
}
