package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.interview.InterviewDatabase;
import seedu.address.model.interview.InterviewRecord;

/**
 * Tests that a {@code Person}'s interview record matches any of the keywords given.
 */
public class InterviewRecordContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;
    private final InterviewDatabase interviewDatabase;

    /**
     * Constructs a predicate with the given keywords and interview database.
     *
     * @param keywords List of keywords to match.
     * @param interviewDatabase The interview database used to retrieve interview records.
     */
    public InterviewRecordContainsKeywordsPredicate(List<String> keywords,
                                                    InterviewDatabase interviewDatabase) {
        this.keywords = keywords;
        this.interviewDatabase = interviewDatabase;
    }

    /**
     * Tests if a {@code Person}'s interview records contain any of the keywords.
     *
     * @param person The person whose interview records are to be tested.
     * @return true if any interview record notes contain the keywords, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        return person.getInterviewIds().stream()
                .filter(interviewDatabase::contains)
                .map(interviewDatabase::getInterviewRecord)
                .map(InterviewRecord::getNotes)
                .anyMatch(this::containsAnyKeyword);
    }

    /**
     * Returns true if the given notes contain any of the keywords.
     *
     * @param notes The interview notes to check.
     * @return true if any keyword is found in the notes, false otherwise.
     */
    private boolean containsAnyKeyword(String notes) {
        String lowerCaseNotes = notes.toLowerCase();
        return keywords.stream()
                .anyMatch(keyword -> lowerCaseNotes.contains(keyword.toLowerCase()));
    }

    /**
     * Returns true if both predicates have the same keywords.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InterviewRecordContainsKeywordsPredicate)) {
            return false;
        }

        InterviewRecordContainsKeywordsPredicate otherPredicate =
                (InterviewRecordContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    /**
     * Returns a string representation of this predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
