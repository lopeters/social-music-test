package luke.crowdmix.user;

import luke.crowdmix.message.MessageRepository;
import org.junit.Test;

import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class UserFactoryTest {
    private static final User.Name USER_NAME = username("Alice");

    private final UserFactory underTest = new UserFactory(mock(MessageRepository.class));

    @Test
    public void createUser() {
        User user = underTest.create(USER_NAME);
        assertThat(user.name().value(), is("Alice"));
    }
}