package luke.crowdmix.message;

import luke.crowdmix.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MessageRepository {
    private final Map<User.Name, List<Message>> messages = new HashMap<>();
    private final Supplier<LocalDateTime> localDateTimeSupplier;

    public MessageRepository(Supplier<LocalDateTime> localDateTimeSupplier) {
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    public void save(User.Name userName, String message) {
        messages.computeIfAbsent(userName, k -> new ArrayList<>()).add(new Message(message, localDateTimeSupplier.get()));
    }

    public List<Message> getAll(User.Name userName) {
        return messages.get(userName);
    }
}
