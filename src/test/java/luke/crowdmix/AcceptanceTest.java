package luke.crowdmix;

import luke.crowdmix.message.Message;
import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.user.UserFactory;
import luke.crowdmix.user.UserRepository;
import org.junit.Test;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class AcceptanceTest {
    private final UserRepository userRepository = new UserRepository(new UserFactory(new MessageRepository()));
    private final PrintStream out = mock(PrintStream.class);
    private final CommandReader commandReader = new CommandReader(userRepository, out);

    @Test
    public void userCanPublishMessagesToAPersonalTimelineAndViewThem() {
        readInput("Alice -> I love the weather today\nBob -> Damn! We lost!\nBob -> Good game though.\n");
        then(out).should(never()).println(any(String.class));

        readInput("Alice\n");
        then(out).should().println("I love the weather today (0 seconds ago)");

        readInput("Bob\n");
        then(out).should().println("Damn! We lost! (0 seconds ago)");
        then(out).should().println("Good game though. (0 seconds ago)");
    }

    private List<Message> userMessagesFor(String alice) {
        return userRepository.get(username(alice)).messages();
    }

    private void readInput(String input) {
        commandReader.consume(new Scanner(input));
    }
}
