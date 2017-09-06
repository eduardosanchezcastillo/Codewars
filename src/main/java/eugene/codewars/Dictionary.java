package eugene.codewars;

/*
    Did you mean ...?
    https://www.codewars.com/kata/5259510fc76e59579e0009d4

I'm sure, you know Google's "Did you mean ...?", when you entered a search term and mistyped a word. In this kata we
want to implement something similar.

You'll get an entered term (lowercase string) and an array of known words (also lowercase strings). Your task is to find
out, which word from the dictionary is most similar to the entered one. The similarity is described by the minimum
number of letters you have to add, remove or replace in order to get from the entered word to one of the dictionary. The
lower the number of required changes, the higher the similarity between each two words.

Same words are obviously the most similar ones. A word that needs one letter to be changed is more similar to another
word that needs 2 (or more) letters to be changed. E.g. the mistyped term berr is more similar to beer (1 letter to be
replaced) than to barrel (3 letters to be changed in total).

Extend the dictionary in a way, that it is able to return you the most similar word from the list of known words.

CODE EXAMPLES:
    Dictionary fruits = new Dictionary(new String[]{"cherry", "pineapple", "melon", "strawberry", "raspberry"});

    fruits.findMostSimilar("strawbery"); // must return "strawberry"
    fruits.findMostSimilar("berry"); // must return "cherry"

    Dictionary things = new Dictionary(new String[]{"stars", "mars", "wars", "codec", "codewars"});
    things.findMostSimilar("coddwars"); // must return "codewars"

    Dictionary languages = new Dictionary(new String[]{"javascript", "java", "ruby", "php", "python", "coffeescript"});
    languages.findMostSimilar("heaven"); // must return "java"
    languages.findMostSimilar("javascript"); // must return "javascript" (same words are obviously the most similar ones)

I know, many of you would disagree that java is more similar to heaven than all the other ones, but in this kata it is ;)


ADDITIONAL NOTES:
there is always exactly one possible solution
*/

public class Dictionary {

    private final String[] words;

    public Dictionary(String[] words) {
        this.words = words;
    }

    public String findMostSimilar(String to) {
        int minChanges = Integer.MAX_VALUE;
        String res = null;

        for (String word : words) {
            int changes = findDistance(word, to);
            if (changes < minChanges) {
                minChanges = changes;
                res = word;
            }
        }

        return res;
    }

    private int findDistance(String s1, String s2) {
        int prev[] = new int[s1.length() + 1];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = i;
        }

        for (int ii = 1; ii <= s2.length(); ii++) {
            int curr[] = new int[s1.length() + 1];
            curr[0] = ii;

            char ch2 = s2.charAt(ii - 1);
            for (int jj = 1; jj <= s1.length(); jj++) {
                char ch1 = s1.charAt(jj - 1);
                if (ch1 == ch2) {
                    curr[jj] = prev[jj - 1];
                } else {
                    curr[jj] = Math.min(curr[jj - 1], Math.min(prev[jj], prev[jj - 1])) + 1;
                }
            }

            prev = curr;
        }

        return prev[s1.length()];
    }

    public static void main(String[] args) {
        Dictionary d = new Dictionary(new String[0]);
        d.test("ABC", "ABF");
        d.test("ABC", "ABFE");
        d.test("ABC", "ABFEEEE");
        d.test("ABC", "ABC");
        d.test("AAAA", "ABBBA");
        d.test("heaven", "java");
        d.test("heaven", "javascript");
    }

    private void test(String s1, String s2) {
        System.out.println(String.format("%s -> %s : %d", s1, s2, findDistance(s1, s2)));
    }
}