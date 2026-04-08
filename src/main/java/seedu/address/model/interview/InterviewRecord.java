package seedu.address.model.interview;

import java.util.Objects;

/**
 * Represents an interview record with an id, date, and notes.
 */
public class InterviewRecord {

    private final String id;
    private final String notes;

    /**
     * Creates an interview record.
     *
     * @param id    The unique id of the interview record.
     * @param notes The interview notes.
     */
    public InterviewRecord(String id, String notes) {
        this.id = Objects.requireNonNull(id);
        this.notes = Objects.requireNonNull(notes);
    }

    public String getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InterviewRecord)) {
            return false;
        }

        InterviewRecord otherRecord = (InterviewRecord) other;
        return id.equals(otherRecord.id)
                && notes.equals(otherRecord.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, notes);
    }

    @Override
    public String toString() {
        return " " + notes;
    }
}

