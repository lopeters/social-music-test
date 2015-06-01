package luke.crowdmix.message;

import luke.crowdmix.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private final String message;
    private final LocalDateTime dateCreated;

    public Message(String message, LocalDateTime dateCreated) {
        this.message = message;
        this.dateCreated = dateCreated;
    }

    public String toString() {
        return "message="  + message + ",dateCreated=" + dateCreated;
    }

    public String message() {
        return message;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(message, message1.message) &&
                Objects.equals(dateCreated, message1.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, dateCreated);
    }
}
