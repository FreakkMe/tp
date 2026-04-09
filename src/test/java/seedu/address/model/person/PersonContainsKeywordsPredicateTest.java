package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));

        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_structuralKeywords_returnsFalse() {
        // Searching toString() labels should NOT match anyone — these are bugs if they return true
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Person"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("name"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("phone"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("email"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("address"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("tags"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Partial phone number match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("9123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Partial email match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Case-insensitive
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Clementi"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Clementi Ave").build()));

        // Mixed-case
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("cLeMeNtI"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Clementi Ave").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("fRiEnDs"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // One of multiple tags matches
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }

    @Test
    public void test_keywordMatchesNoField_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("zzznomatch"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("123 Clementi Ave")
                .withTags("friends")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
