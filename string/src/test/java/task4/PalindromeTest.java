package task4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTestCases")
    public void testIsPalindrome(String input, boolean expected) {
        assertEquals(expected, PalindromeChecker.isPalindrome(input));
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                // The input will be a string that may contain alphabetic characters
                Arguments.of("Was it a car or a cat I saw?", true),
                Arguments.of("Madam, in Eden, I'm Adam", true),
                Arguments.of("Never odd or even", true),
                Arguments.of("Don't nod", true),
                Arguments.of("Sir, I demand, I am a maid named Iris", true),
                Arguments.of("Eva, can I see bees in a cave?", true),
                Arguments.of("Madam, in Eden, I'm Adam", true),
                Arguments.of("Never a foot too far, even", true),
                Arguments.of("Red roses run no risk, sir, on Nurse's order", true),
                Arguments.of("A Santa at NASA", true),
                Arguments.of("Mr. Owl ate my metal worm", true),
                Arguments.of("Eva, can I see bees in a cave?", true),
                Arguments.of("Red roses", false),
                Arguments.of("Hello, World!", false),
                Arguments.of("not a palindrome", false),
                Arguments.of("definitely not a palindrome", false),
                Arguments.of("this is not a palindrome", false),
                Arguments.of("neither is this", false),
                Arguments.of("nor is this one", false),
                Arguments.of("this isn't a palindrome either", false),
                Arguments.of("this is also not a palindrome", false),
                Arguments.of("and this isn't a palindrome", false),
                Arguments.of("this isn't one either", false),
                Arguments.of("nor is this", false),
                // The input will be a string that may contain alphabetic numbers
                Arguments.of("1", true),
                Arguments.of("12321", true),
                Arguments.of("1221", true),
                Arguments.of("11111", true),
                Arguments.of("1234321", true),
                Arguments.of("1112111", true),
                Arguments.of("1212121", true),
                Arguments.of("123321", true),
                Arguments.of("11111", true),
                Arguments.of("123454321", true),
                Arguments.of("1234567654321", true),
                Arguments.of("12345", false),
                Arguments.of("123456", false),
                Arguments.of("1234567", false),
                Arguments.of("12345678", false),
                Arguments.of("123456789", false),
                Arguments.of("1234567890", false),
                Arguments.of("23456789", false),
                Arguments.of("3456789", false),
                Arguments.of("456789", false),
                Arguments.of("56789", false),
                Arguments.of("12", false),
                // The input will be a string that may contain different spaces
                Arguments.of("don' t nod", true),
                Arguments.of("don't   nod", true),
                Arguments.of("nurses  run", true),
                Arguments.of("nurses   run", true),
                Arguments.of("was it a car  or a cat i saw", true),
                Arguments.of("was  it a    car      or a cat i saw", true),
                Arguments.of("was it a car\tor a        cat\ni saw", true),
                Arguments.of("was it a car or a cat i     saw", true),
                Arguments.of("was it a car or a cat\ni saw", true),
                Arguments.of("was it a car or a cat \ti saw", true),
                Arguments.of("was it a car or a cat i saw", true),
                Arguments.of("yo  banana boy", true),
                Arguments.of("sir i demand i \t am a maid named iris", true),
                Arguments.of("was it a car or \n a cat i saw", true),
                Arguments.of("eva can i see \t bees in a cave", true),
                Arguments.of("madam in \t eden i'm adam", true),
                Arguments.of("\tnever odd or even\n", true),
                Arguments.of("don't nod\t", true),
                Arguments.of("\trunning nurses\n", false),
                Arguments.of("this is not a palindrome", false),
                Arguments.of("neither is this", false),
                Arguments.of("nor is this one", false),
                Arguments.of("this isn't a palindrome either", false),
                Arguments.of("this is also not a palindrome", false),
                Arguments.of("and this isn't a palindrome", false),
                Arguments.of("this isn't one either", false),
                Arguments.of("nor is this", false),
                // The input will be a string that may contain punctuation
                Arguments.of("Able, was I saw Elba.", true),
                Arguments.of("Madam, in Eden, I'm Adam.", true),
                Arguments.of("A man, a plan, a canal, Panama.", true),
                Arguments.of("Was it a car or a cat I saw?", true),
                Arguments.of("Yo, Banana Boy!", true),
                Arguments.of("Eva! can I see bees in a cave?", true),
                Arguments.of("Madam, in Eden% I'm Adam.", true),
                Arguments.of("Never odd or even.", true),
                Arguments.of("Don't nod.", true),
                Arguments.of("Sir, I'm Iris.", true),
                Arguments.of("This, is not a palindrome.", false),
                Arguments.of("Neither, is this.", false),
                Arguments.of("Nor is this one.", false),
                Arguments.of("This isn't a palindrome either.", false),
                Arguments.of("This is also not a palindrome.", false),
                Arguments.of("And this isn't a palindrome.", false),
                Arguments.of("This isn't one either.", false),
                Arguments.of("Nor is this.", false),
                // The input will be a string that may contain a mix of alphabetic characters, numbers, spaces, and punctuation.
                Arguments.of("A man, a plan, a canal: Panama", true),
                Arguments.of("Was it a car or a cat I saw?", true),
                Arguments.of("Madam, in Eden, I'm Adam.", true),
                Arguments.of("Don't nod.", true),
                Arguments.of("Sir, I'm Iris.", true),
                Arguments.of("Was it a car or a cat I saw?", true),
                Arguments.of("Yo, Banana Boy!", true),
                Arguments.of("Eva, can I see bees in a cave?", true),
                Arguments.of("Never odd or even.", true),
                Arguments.of("Don't nod.", true),
                Arguments.of("This is not a palindrome, even with punctuation!", false),
                Arguments.of("Neither is this one, even with punctuation!", false),
                Arguments.of("Nor is this one, even with punctuation!", false),
                Arguments.of("This isn't a palindrome either, even with punctuation!", false),
                Arguments.of("This is also not a palindrome, even with punctuation!", false),
                Arguments.of("And this isn't a palindrome, even with punctuation!", false),
                Arguments.of("This isn't one either, even with punctuation!", false),
                Arguments.of("Nor is this, even with punctuation!", false)
        );
    }
}
