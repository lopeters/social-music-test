package luke.crowdmix.user;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.util.MicroType;

import java.util.List;

public class User {
    private final Name name;
    private final MessageRepository messageRepository;

    public User(Name name, MessageRepository messageRepository) {
        this.name = name;
        this.messageRepository = messageRepository;
    }

    public void publish(String message) {
        messageRepository.save(name, message);
    }

    public List<Message> messages() {
        return messageRepository.getAll(name);
    }

    public Name name() {
        return name;
    }

    public static class Name extends MicroType<String> {
        public Name(String value) {
            super(value);
        }

        public static Name username(String name) {
            return new Name(name);
        }
    }
}
