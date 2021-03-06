/**
 * Implement a trie with insert, search, and startsWith methods.
 */

/*
    You may assume that all inputs are consist of lowercase letters a-z.

    Example:

    insert("lintcode")
    search("code")
    >>> false
    startsWith("lint")
    >>> true
    startsWith("linterror")
    >>> false
    insert("linterror")
    search("lintcode)
    >>> true
    startsWith("linterror")
    >>> true
 */



    /*  
        What can Trie do:
            1. Did the entire word there?
            2. Did the prefix there?

        -----------------------------------------------------------------------
        Comparing Hash and Trie: 

        1. Space complexity Trie is smaller Hash:
            Example : ['a', 'aa', 'aaa', 'aaaa']
        2. Time complexity is actually the same. O(k) where k is the length of
        the string. 
        3. Actions:
            Trie can deal with not only the entire word but also the prefix.
            But Trie is not easy to implement. And we must complete this within
            15 minutes in the interview.
        -----------------------------------------------------------------------                    
     */  

///////////////////////
// Array of TrieNode //
///////////////////////

/**
 * Your Trie object will be instantiated and called as such:
 * Trie trie = new Trie();
 * trie.insert("lintcode");
 * trie.search("lint"); will return false
 * trie.startsWith("lint"); will return true
 */

/* 1. TrieNode */
class TrieNode {
    public boolean hasWord;
    private TrieNode[] children; /* Need an array full of TrieNode */ 
    
    /* Constructor */
    public TrieNode() {
        children = new TrieNode[26];
        hasWord = false;
    }
    /* Insert: 1. length, position, recursion */    
    public void insert(String word, int index) {
        if (index == word.length()) {
            this.hasWord = true;
            return;
        }
        int pos = word.charAt(index) - 'a';
        if (children[pos] == null) {
            children[pos] = new TrieNode();
        }
        children[pos].insert(word, index + 1);
    }
    /* Find: 1. length, position, recursion */
    public TrieNode find(String word, int index) {
        if (index == word.length()) {
            return this;
        }
        int pos = word.charAt(index) - 'a';
        if (children[pos] == null) {
            return null;
        }
        return children[pos].find(word, index + 1);
    }
}

/* 2. Trie class */
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /* Inserts a word into the trie. */
    public void insert(String word) {
        root.insert(word, 0);
    }

    /* Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = root.find(word, 0);
        return node != null && node.hasWord;
    }

    /* Returns if there is any word in the trie */
    /* that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode node = root.find(prefix, 0);
        return node != null;
    }
}

/////////////////////
// Using a HashMap //
/////////////////////

/* StraightForward TrieNode */
class TrieNode {
    public char c;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean hasWord;
    public TrieNode() {}
    public TrieNode(char ch) {
        c = ch;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /* Insert */
    public void insert(String word) {
        /* Get cur */
        TrieNode cur = root;
        HashMap<Character, TrieNode> curChildren = root.children;
        /* Better performance than String.charAt() */

        char[] wordArray = word.toCharArray(); 
        /* Traverse each character in the word */
        for (int i = 0; i < wordArray.length; i++) {
            char wc = wordArray[i];
            if (curChildren.containsKey(wc)) { /* Find key, go deeper */
                cur = curChildren.get(wc);
            } else { /* Create the children and go deeper */
                TrieNode newNode = new TrieNode(wc); /* Deep copy */
                curChildren.put(wc, newNode);
                cur = newNode;
            }
            curChildren = cur.children; /* Set children */
            /* The entire word */
            if (i == wordArray.length - 1) {
                cur.hasWord = true; /* set hasWord inside the for loop */
            }
        }
    }
    /* Search */
    public boolean search(String word) {
        if (searchWordNodePos(word) == null) {
            return false;
        }
        if (searchWordNodePos(word).hasWord) {
            return true;
        } 
        return false;
    }
    /* StartWith */
    public boolean startsWith(String prefix) {
        if (searchWordNodePos(prefix) == null) {
            return false;
        } 
        return true;
    }
    
    /* Search Position -- This is a helper */
    public TrieNode searchWordNodePos(String word) {
        HashMap<Character, TrieNode> children = root.children;
        TrieNode cur = null;
        char[] wordArray = word.toCharArray();
        for (int i = 0; i < wordArray.length; i++) {
            char c = wordArray[i];
            if (children.containsKey(c)) {
                cur = children.get(c);
                children = cur.children;
            } else {
                return null;
            }
        }
        return cur;
    }
}



