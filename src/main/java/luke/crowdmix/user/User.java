package luke.crowdmix.user;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.util.MicroType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class User {
    private final Name name;
    private final MessageRepository messageRepository;
    private final Set<Name> following = new HashSet<>();

    public User(Name name, MessageRepository messageRepository) {
        this.name = name;
        this.messageRepository = messageRepository;
    }

    public void publish(String message) {
        messageRepository.save(name, message);
    }

    public Collection<Message> messages() {
        return messageRepository.getAll(name);
    }

    public Name name() {
        return name;
    }

    public void follow(Name name) {
        following.add(name);
    }

    public Collection<Message> wall() {
        return Stream.concat(following.stream().flatMap(name -> messageRepository.getAll(name).stream()), messages().stream())
                .sorted((o1, o2) -> o2.dateCreated().compareTo(o1.dateCreated()))
                .collect(toList());
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
