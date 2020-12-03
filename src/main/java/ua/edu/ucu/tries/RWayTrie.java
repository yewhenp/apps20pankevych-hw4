package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;
import ua.edu.ucu.collections.immutable.ImmutableArrayList;

import java.util.ArrayList;

public class RWayTrie implements Trie {
    private static final int R = 26; // radix
    private final Node root = new Node(' '); // root of trie
    private int size = 0;
    private static final int CHARSHIFT = 96;

    @Override
    public void add(Tuple t) {
        if (contains(t.term)) {
            return;
        }

        Node node = root;
        Node lastNode = null;
        int i = 0;
        for (; i < t.weight; i++) {
            lastNode = node;
            //lastNode.lengthNext += 1;
            node = node.next[(int) t.term.charAt(i) - CHARSHIFT];
            if (node == null) {
                break;
            }
        }

        for (; i < t.weight; i++) {
            Character charr = t.term.charAt(i);
            node = new Node(charr);
            lastNode.lengthNext += 1;
            lastNode.next[(int) charr - CHARSHIFT] = node;
            lastNode = node;
        }
        lastNode.wordEnd = true;
        size += 1;
    }

    @Override
    public boolean contains(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            if (node.next[(int) word.charAt(i) - CHARSHIFT] == null) {
                return false;
            }
            node = node.next[(int) word.charAt(i) - CHARSHIFT];
        }
        return node.wordEnd;
    }

    @Override
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        }

        Node node = root;
        deleteRecursive(node, word, word.charAt(0), new Flag());
        size -= 1;
        return true;
    }

    private void checkMore(Node node, Flag flag) {
        if (node.lengthNext > 1 || node.lengthNext == 0) {
            flag.flag = true;
        }
        for (int i = 0; i < R; i++) {
            if (node.next[i] != null) {
                checkMore(node.next[i], flag);
            }
        }
    }

    private void deleteRecursive(Node node, String word,
                                 Character charr, Flag flag) {
        if (word.length() == 0) {
            checkMore(node, flag);
            if (!flag.flag) {
                node.next[charr - CHARSHIFT] = null;
                node.lengthNext -= 1;
            } else {
                node.wordEnd = false;
            }
            return;
        }
        Character charra = word.charAt(0);
        String newWord = word.substring(1);

        deleteRecursive(node.next[(int) charra - CHARSHIFT],
                newWord, charra, flag);

        if (!flag.flag) {
            if (node.lengthNext > 1) {
                flag.flag = true;
                return;
            }
            node.next[(int) charra - CHARSHIFT] = null;
            node.lengthNext -= 1;
        }
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue(new ImmutableArrayList());
        ArrayList<String> result = new ArrayList<String>();

        Node node = root;
        for (int i = 0; i < s.length(); i++) {
            node = node.next[(int) s.charAt(i) - CHARSHIFT];
            if (node == null) {
                return result;
            }
        }

        collect(node, s, q);

        while (q.length() > 0) {
            result.add((String) q.dequeue());
        }
        return result;
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null) {
            return;
        }
        if (x.val != null && x.wordEnd) {
            q.enqueue(pre);
        }
        for (int c = 0; c < R; c++) {
            if (x.next[c] == null) {
                continue;
            }
            collect(x.next[c], pre + x.next[c].val, q);
        }
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node {
        private final Character val;
        private final Node[] next = new Node[R];
        private int lengthNext = 0;
        private boolean wordEnd = false;

        private Node(Character value) {
            this.val = value;
        }
    }

    private static class Flag {
        private boolean flag = false;
    }

}
