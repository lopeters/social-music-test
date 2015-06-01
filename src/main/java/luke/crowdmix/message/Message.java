package luke.crowdmix.message;

import luke.crowdmix.util.DateUtil;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class Message {
    private final String message;
    private final LocalDateTime dateTime;
    private final Supplier<LocalDateTime> dateTimeSupplier;

    public Message(String message, Supplier<LocalDateTime> dateTimeSupplier) {
        this.message = message;
        this.dateTimeSupplier = dateTimeSupplier;
        this.dateTime = dateTimeSupplier.get();
    }

    public String toString() {
        return message + " (" + DateUtil.describeDuration(dateTime, dateTimeSupplier.get()) + " ago)";
    }

    public static Message message(String message) {
        return new Message(message, LocalDateTime::now);
    }
}
