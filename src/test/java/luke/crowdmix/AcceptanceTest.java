package luke.crowdmix;

import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.user.UserFactory;
import luke.crowdmix.user.UserRepository;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class AcceptanceTest {
    public static final LocalDateTime CURRENT_TIME = LocalDateTime.now();

    private final Iterator<LocalDateTime> creationDateTimes = asList(
            CURRENT_TIME.minusMinutes(5),
            CURRENT_TIME.minusMinutes(2),
            CURRENT_TIME.minusMinutes(1),
            CURRENT_TIME.minusSeconds(15)).iterator();
    private final PrintStream out = mock(PrintStream.class);
    private final UserRepository userRepository = new UserRepository(new UserFactory(new MessageRepository(creationDateTimes::next)));
    private final CommandReader commandReader = new CommandReader(userRepository, out, () -> CURRENT_TIME);

    @Test
    public void userCanPublishMessagesToAPersonalTimelineAndViewThem() {
        readInput("Alice -> I love the weather today", "Bob -> Damn! We lost!", "Bob -> Good game though.");
        then(out).should(never()).println(any(String.class));

        readInput("Alice");
        then(out).should().println("I love the weather today (5 minutes ago)");

        readInput("Bob");
        InOrder inOrder = inOrder(out);
        inOrder.verify(out).println("Good game though. (1 minute ago)");
        inOrder.verify(out).println("Damn! We lost! (2 minutes ago)");

        readInput("Charlie -> I'm in New York today! Anyone wants to have a coffee?", "Charlie follows Alice", "Charlie wall");
        inOrder = inOrder(out);
        inOrder.verify(out).println("Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)");
        inOrder.verify(out).println("Alice - I love the weather today (5 minutes ago)");

        readInput("Charlie follows Bob", "Charlie wall");
        inOrder = inOrder(out);
        inOrder.verify(out).println("Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)");
        inOrder.verify(out).println("Bob - Good game though. (1 minute ago)");
        inOrder.verify(out).println("Bob - Damn! We lost! (2 minutes ago)");
        inOrder.verify(out).println("Alice - I love the weather today (5 minutes ago)");
    }

    private void readInput(String... input) {
        commandReader.consume(new Scanner(asList(input).stream().collect(Collectors.joining("\n"))));
    }
}
