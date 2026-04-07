package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.InterviewRecord;

/**
 * Jackson-friendly version of {@link InterviewRecord}.
 */
class JsonAdaptedInterviewRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "InterviewRecord's %s field is missing!";

    private final String id;
    private final String notes;

    @JsonCreator
    public JsonAdaptedInterviewRecord(@JsonProperty("id") String id,
                                      @JsonProperty("notes") String notes) {
        this.id = id;
        this.notes = notes;
    }

    public JsonAdaptedInterviewRecord(InterviewRecord source) {
        id = source.getId();
        notes = source.getNotes();
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code InterviewRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InterviewRecord toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }
        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes"));
        }

        return new InterviewRecord(id, notes);
    }
}

