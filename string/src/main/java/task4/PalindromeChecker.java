package task4;

public class PalindromeChecker {

    private PalindromeChecker() {
    }

    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        String cleaned = text.replaceAll("\\W", "").toLowerCase();
        StringBuilder reversed = new StringBuilder(cleaned).reverse();
        return cleaned.contentEquals(reversed);
    }
}