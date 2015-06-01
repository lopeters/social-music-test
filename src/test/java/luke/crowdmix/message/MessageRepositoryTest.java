package luke.crowdmix.message;

import luke.crowdmix.user.User;
import org.junit.Test;

import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class MessageRepositoryTest {
    private static final User.Name USER = username("Alice");
    private static final User.Name ANOTHER_USER = username("Bob");

    private final MessageRepository underTest = new MessageRepository();

    @Test
    public void canGetMessagesAfterSavingThem() {
        underTest.save(USER, "message");
        underTest.save(ANOTHER_USER, "another message");
        underTest.save(ANOTHER_USER, "yet another message");

        assertThat(underTest.getAll(USER), contains("message"));
        assertThat(underTest.getAll(ANOTHER_USER), contains("another message", "yet another message"));
    }
}