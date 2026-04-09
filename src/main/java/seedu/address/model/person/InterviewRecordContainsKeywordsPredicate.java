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

    public InterviewRecordContainsKeywordsPredicate(List<String> keywords, InterviewDatabase interviewDatabase) {
        this.keywords = keywords;
        this.interviewDatabase = interviewDatabase;
    }

    @Override
    public boolean test(Person person) {
        return person.getInterviewIds().stream()
                .filter(interviewDatabase::contains)
                .map(interviewDatabase::getInterviewRecord)
                .map(InterviewRecord::getNotes)
                .anyMatch(this::containsAnyKeyword);
    }

    private boolean containsAnyKeyword(String notes) {
        String lowerCaseNotes = notes.toLowerCase();
        return keywords.stream()
                .anyMatch(keyword -> lowerCaseNotes.contains(keyword.toLowerCase()));
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}