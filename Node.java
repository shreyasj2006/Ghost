import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shreyas on 7/21/2015.
 */
public class Node {
    char _char;
    HashMap<Character,Node> nextNodes;
    int depth;
    int bitmask = 0;

    Node(char charValue) {
        nextNodes = new HashMap<Character, Node>();
        _char = charValue;
    }

    public void addNextNode(Node node) {
        //this.nextNodes.add(node);
    }

    public char getChar() {
        return this._char;
    }

    public void addWord(String word) {
        for(int i = 1; i < word.length(); ++i) {
            int charAscii = (int) word.charAt(i);
            int bitIndex = charAscii - 97;
            if((bitmask & (1 << bitIndex)) == 1) {

            }
        }
    }
}
