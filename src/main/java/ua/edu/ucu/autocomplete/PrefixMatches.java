package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String string: strings) {
            String[] wordArray = string.split(" ");
            for (String word: wordArray) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return 1;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterable<String> data = wordsWithPrefix(pref);
        List<String> result = new ArrayList<>();
        for (String word : data) {
            if (word.length() < pref.length() + k) {
                result.add(word);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
