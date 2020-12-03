package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;

public class RWayTrie implements Trie {
    private static int R = 26; // radix
    private Node root = new Node(' '); // root of trie
    private static class Node
    {
        private Character val;
        private Node[] next = new Node[R];

        private Node(Character value){
            this.val = value;
        }
    }

    @Override
    public void add(Tuple t) {
        Node node = root;
        Node lastNode = null;
        int i = 0;
        for (; i < t.weight; i++){
            lastNode = node;
            node = node.next[(int) t.term.charAt(i)];
            if (node == null){
                break;
            }
        }

        for (; i < t.weight; i++){
           Character charr = t.term.charAt(i);
           node = new Node(charr);
           lastNode.next[(int) charr] = node;
           lastNode = node;
        }
    }

    @Override
    public boolean contains(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++){
            if (node.next[(int) word.charAt(i)] == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(String word) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    @Override
    public int size()
    {
        return size(root);
    }

    private int size(Node x)
    {
        if (x == null) return 0;
        int cnt = 0;
        if (x.val != null) cnt++;
        for (char c = 0; c < R; c++)
            cnt += size(next[c]);
        return cnt;
    }

}
