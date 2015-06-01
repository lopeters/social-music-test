package luke.crowdmix.message;

import luke.crowdmix.user.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class MessageRepositoryTest {
    private static final User.Name USER = username("Alice");
    private static final User.Name ANOTHER_USER = username("Bob");
    private static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.now();

    private final MessageRepository underTest = new MessageRepository(() -> CREATION_DATE_TIME);

    @Test
    public void canGetMessagesAfterSavingThemOrderedByNewest() {
        underTest.save(USER, "message");
        underTest.save(ANOTHER_USER, "another message");
        underTest.save(ANOTHER_USER, "yet another message");

        assertThat(underTest.getAll(USER), contains(new Message("message", CREATION_DATE_TIME, USER)));
        assertThat(underTest.getAll(ANOTHER_USER), contains(new Message("yet another message", CREATION_DATE_TIME, ANOTHER_USER), new Message("another message", CREATION_DATE_TIME, ANOTHER_USER)));
    }
}