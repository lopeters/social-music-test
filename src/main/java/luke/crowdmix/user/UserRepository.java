package luke.crowdmix.user;

import java.util.HashMap;

public class UserRepository {
    private final UserFactory userFactory;
    private final HashMap<User.Name, User> users = new HashMap<>();

    public UserRepository(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public User get(User.Name name) {
        return users.computeIfAbsent(name, userFactory::create);
    }
}
