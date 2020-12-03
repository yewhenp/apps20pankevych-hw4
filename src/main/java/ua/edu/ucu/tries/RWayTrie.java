package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;
import ua.edu.ucu.collections.immutable.ImmutableArrayList;

import java.util.ArrayList;

public class RWayTrie implements Trie {
    private static int R = 26; // radix
    private Node root = new Node(' '); // root of trie
    private int size = 0;
    private static class Node
    {
        private Character val;
        private Node[] next = new Node[R];
        private int lengthNext = 0;
        private boolean wordEnd = false;

        private Node(Character value){
            this.val = value;
        }
    }

    @Override
    public void add(Tuple t) {
        if (contains(t.term)) return;

        Node node = root;
        Node lastNode = null;
        int i = 0;
        for (; i < t.weight; i++){
            lastNode = node;
            //lastNode.lengthNext += 1;
            node = node.next[(int) t.term.charAt(i) - 96];
            if (node == null){
                break;
            }
        }

        for (; i < t.weight; i++){
           Character charr = t.term.charAt(i);
           node = new Node(charr);
           lastNode.lengthNext += 1;
           lastNode.next[(int) charr - 96] = node;
           lastNode = node;
        }
        lastNode.wordEnd = true;
        size += 1;
    }

    @Override
    public boolean contains(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++){
            if (node.next[(int) word.charAt(i) - 96] == null){
                return false;
            }
            node = node.next[(int) word.charAt(i) - 96];
        }
        return node.wordEnd;
    }

    @Override
    public boolean delete(String word) {
        if (!contains(word)){
            return false;
        }

        Node node = root;
        deleteRecursive(node, word, word.charAt(0), new Flag());
//        for (int i = 0; i < word.length(); i++){
//            if (node.next[word.charAt(i) - 96] == null || node.lengthNext == 1){
//                deleteRecursive(node.next[word.charAt(i) - 96], word.substring(i), new Flag());
//                break;
//            }
//            node.lengthNext -= 1;
//            node = node.next[word.charAt(i) - 96];
//        }
        size -= 1;
        return true;
    }

    private static class Flag{
        private boolean flag = false;
    }

    private void checkMore(Node node, Flag flag){
        if (node.lengthNext > 1){
            flag.flag = true;
        }
        for (int i = 0; i < R; i++){
            if (node.next[i] != null){
                checkMore(node.next[i], flag);
            }
        }
    }

    private void deleteRecursive(Node node, String word, Character charr, Flag flag){
        if (word.length() == 0) {
            checkMore(node, flag);
            if (!flag.flag){
                node.next[charr - 96] = null;
                node.lengthNext -= 1;
            }
            return;
        }
        Character charra = word.charAt(0);
        word = word.substring(1);

        deleteRecursive(node.next[(int) charra - 96], word, charra, flag);


        if (!flag.flag){
            node.next[(int) charra - 96] = null;
            node.lengthNext -= 1;
            if (node.lengthNext > 0){
                flag.flag = true;
            }
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
        for (int i = 0; i < s.length(); i++){
            node = node.next[(int)s.charAt(i) - 96];
            if (node == null) return result;
        }

        collect(node, s, q);

        while (q.length() > 0){
            result.add((String) q.dequeue());
        }
        return result;
    }

    private void collect(Node x, String pre, Queue q){
        if (x == null) return;
        if (x.val != null && x.wordEnd) q.enqueue(pre);
        for (int c = 0; c < R; c++){
            if (x.next[c] == null) continue;
            collect(x.next[c], pre + x.next[c].val, q);
        }
    }

    @Override
    public int size()
    {
        return size;
    }

}
