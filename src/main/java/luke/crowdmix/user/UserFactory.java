package luke.crowdmix.user;

import luke.crowdmix.message.MessageRepository;

public class UserFactory {
    private final MessageRepository messageRepository;

    public UserFactory(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public User create(User.Name name) {
        return new User(name, messageRepository);
    }
}
