package eugene.codewars;

/*
    Strip Comments
    https://www.codewars.com/kata/strip-comments

Complete the solution so that it strips all text that follows any of a set of comment markers passed in. Any whitespace
at the end of the line should also be stripped out.

Given an input string of:
    apples, pears # and bananas
    grapes
    bananas !apples

The output expected would be:
    apples, pears
    grapes
    bananas

The code would be called like so:
    var result = solution("apples, pears # and bananas\ngrapes\nbananas !apples", ["#", "!"])
    // result should == "apples, pears\ngrapes\nbananas"
*/

public class StripComments {
    public static String stripComments(String text, String[] commentSymbols) {
        String cs = String.join("", commentSymbols);
        return text.replaceAll("\\h*[" + cs + "]+.*(\\n?)", "$1")
                .replaceAll("\\h+(\\n)", "$1")
                .replaceAll("\\h+\\z", "");
    }
}
