package luke.crowdmix;

import luke.crowdmix.message.MessageRepository;
import luke.crowdmix.user.UserFactory;
import luke.crowdmix.user.UserRepository;
import org.junit.Test;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class AcceptanceTest {
    public static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.of(2015, 6, 1, 12, 0);

    private final UserRepository userRepository = new UserRepository(new UserFactory(new MessageRepository(() -> CREATION_DATE_TIME)));
    private final PrintStream out = mock(PrintStream.class);
    private final Iterator<LocalDateTime> dateTimes = asList(CREATION_DATE_TIME.plusMinutes(5), CREATION_DATE_TIME.plusMinutes(1), CREATION_DATE_TIME.plusMinutes(2)).iterator();
    private final CommandReader commandReader = new CommandReader(userRepository, out, dateTimes::next);

    @Test
    public void userCanPublishMessagesToAPersonalTimelineAndViewThem() {
        readInput("Alice -> I love the weather today\nBob -> Damn! We lost!\nBob -> Good game though.\n");
        then(out).should(never()).println(any(String.class));

        readInput("Alice\n");
        then(out).should().println("I love the weather today (5 minutes ago)");

        readInput("Bob\n");
        then(out).should().println("Damn! We lost! (1 minute ago)");
        then(out).should().println("Good game though. (2 minutes ago)");
    }

    private void readInput(String input) {
        commandReader.consume(new Scanner(input));
    }
}
