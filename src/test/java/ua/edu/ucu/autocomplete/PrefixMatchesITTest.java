
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;
    private PrefixMatches pmWord;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
        pmWord = new PrefixMatches(new RWayTrie());
        pmWord.load("hello yevhen", "how are you", "how do you do",
                "hey look", "looking at you", "yogurt", "lobster", "yourself");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefixMain() {
        String pref = "he";
        Iterable<String> result = pmWord.wordsWithPrefix(pref);
        String[] expResult = {"hello", "hey"};
        assertThat(result, containsInAnyOrder(expResult));

        pref = "yo";
        result = pmWord.wordsWithPrefix(pref);
        expResult = new String[]{"you", "yogurt", "yourself"};
        assertThat(result, containsInAnyOrder(expResult));

        assertEquals(pmWord.size(), 11);

        pmWord.delete("yourself");
        result = pmWord.wordsWithPrefix(pref);
        expResult = new String[]{"yogurt", "you"};
        assertThat(result, containsInAnyOrder(expResult));

        assertEquals(pmWord.size(), 10);

        assertTrue(pmWord.contains("yevhen"));
        assertFalse(pmWord.delete("linux"));
    }

}
