package luke.crowdmix.user;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.user.User;
import org.junit.Test;
import org.mockito.Mockito;

import static java.util.Arrays.asList;
import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class UserTest {
    private static final User.Name USERNAME = username("Alice");

    private final MessageRepository messageRepository = mock(MessageRepository.class);
    private final User underTest = new User(USERNAME, messageRepository);

    @Test
    public void messageIsSavedToRepositoryWhenPublished() {
        underTest.publish("message");
        then(messageRepository).should().save(USERNAME, "message");
    }

    @Test
    public void messageIsReadFromRepository() {
        Message firstMessage = Mockito.mock(Message.class);
        Message secondMessage = Mockito.mock(Message.class);
        given(messageRepository.getAll(USERNAME)).willReturn(asList(firstMessage, secondMessage));
        assertThat(underTest.messages(), contains(firstMessage, secondMessage));
    }
}
