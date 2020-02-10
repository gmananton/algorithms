import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anton Mikhaylov on 10.02.2020.
 */
public class Palindrome {

    public static void main(String[] args) {
        System.out.println(isPalyndrome("я иду с мечем судия"));
        System.out.println(isPalyndrome("яидус мечем судия"));
        System.out.println(isPalyndrome("яидусмечемсудия"));
        System.out.println(isPalyndrome(("q")));
        System.out.println(isPalyndrome(("aaa")));
        System.out.println(isPalyndrome(("aaaa")));
        System.out.println(isPalyndrome(("aa a a")));
        System.out.println(isPalyndrome(("asfasdf a a")));
    }

    private static boolean isPalyndrome(String s) {
        Set<Character> exclusions = new HashSet<Character>() {{
            add(' ');
            add(',');
            add('?');
            add('!');
        }};

        char[] arr = s.toCharArray();
        if (arr.length == 1) return true;

        int leftPos = 0;
        int rightPos = arr.length - 1;

        for (; leftPos < arr.length; leftPos++) {
            if (exclusions.contains(arr[leftPos])) {
                leftPos++;
            }
            if (exclusions.contains(arr[rightPos])) {
                rightPos--;
            }

            if (arr[leftPos] != arr[rightPos]) {
                return false;
            }

            rightPos--;
        }

        return true;
    }
}
