package luke.crowdmix.user;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class UserTest {
    private static final User.Name USERNAME = username("Alice");
    private static final User.Name ANOTHER_USERNAME = username("Bob");
    private static final User.Name YET_ANOTHER_USERNAME = username("Charlie");

    private final MessageRepository messageRepository = mock(MessageRepository.class);
    private final User user = new User(USERNAME, messageRepository);

    @Test
    public void messageIsSavedToRepositoryWhenPublished() {
        user.publish("message");
        then(messageRepository).should().save(USERNAME, "message");
    }

    @Test
    public void messagesAreReadFromRepository() {
        Message firstMessage = new Message("foo", LocalDateTime.now(), USERNAME);
        Message secondMessage = new Message("bar", LocalDateTime.now(), USERNAME);
        given(messageRepository.getAll(USERNAME)).willReturn(asList(firstMessage, secondMessage));

        assertThat(user.messages(), contains(firstMessage, secondMessage));
    }

    @Test
    public void wallAlwaysDisplaysOwnMessages() {
        Message firstMessage = new Message("foo", LocalDateTime.now(), USERNAME);
        Message secondMessage = new Message("bar", LocalDateTime.now(), USERNAME);
        given(messageRepository.getAll(USERNAME)).willReturn(asList(firstMessage, secondMessage));

        assertThat(user.wall(), contains(firstMessage, secondMessage));
    }

    @Test
    public void followedUserMessagesAppearOnTimelineOrderedByDate() {
        Message earliestMessage = new Message("hello world", LocalDateTime.MIN, USERNAME);
        Message middleMessage = new Message("world in motion", LocalDateTime.now(), ANOTHER_USERNAME);
        Message latestMessage = new Message("goodbye world", LocalDateTime.MAX, YET_ANOTHER_USERNAME);
        given(messageRepository.getAll(USERNAME)).willReturn(singletonList(earliestMessage));
        given(messageRepository.getAll(ANOTHER_USERNAME)).willReturn(singletonList(middleMessage));
        given(messageRepository.getAll(YET_ANOTHER_USERNAME)).willReturn(singletonList(latestMessage));

        user.follow(ANOTHER_USERNAME);
        user.follow(YET_ANOTHER_USERNAME);
        Collection<Message> messages = user.wall();
        assertThat(messages, contains(latestMessage, middleMessage, earliestMessage));
    }
}
