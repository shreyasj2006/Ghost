import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shreyas on 7/21/2015.
 */
public class Node {
    char _char;
    HashMap<Character,Node> nextNodes;
    boolean isLeaf;

    Node(char charValue) {
        nextNodes = new HashMap<Character, Node>();
        _char = charValue;
        isLeaf = false;
    }

    public char getChar() {
        return this._char;
    }

    public void addWord(String word, int index) {
        if(index >= word.length()) {
            isLeaf = true;
            return;
        }

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

        for (int i = index; i < word.length(); ++i) {
            if(!this.isLeaf) {
                Node node = new Node(word.charAt(i));
                this.nextNodes.put(word.charAt(i), node);
                if (index + 1 != word.length())
                    node.addWord(word, ++index);
            }
        }
    }

    public HashMap<Character,Node> getNextNodes() {
        return this.nextNodes;
    }

    public int countNextNodes() {
        int count = 0;
        for(Node node: this.nextNodes.values()) {
            count += node.countNextNodes();
        }
        return count;
    }
}
