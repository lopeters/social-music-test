package luke.crowdmix.message;

import luke.crowdmix.user.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class MessageRepository {
    private final Map<User.Name, List<Message>> messages = new LinkedHashMap<>();
    private final Supplier<LocalDateTime> localDateTimeSupplier;

    public MessageRepository(Supplier<LocalDateTime> localDateTimeSupplier) {
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    public void save(User.Name userName, String message) {
        messages.computeIfAbsent(userName, k -> new ArrayList<>()).add(0, new Message(message, localDateTimeSupplier.get(), userName));
    }

    public Collection<Message> getAll(User.Name userName) {
        return messages.get(userName);
    }
}
