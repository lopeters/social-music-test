package luke.crowdmix.message;

import luke.crowdmix.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRepository {
    private final Map<User.Name, List<Message>> messages = new HashMap<>();

    public void save(User.Name userName, String message) {
        messages.computeIfAbsent(userName, k -> new ArrayList<>()).add(new Message(message, LocalDateTime::now));
    }

    public List<Message> getAll(User.Name userName) {
        return messages.get(userName);
    }
}
