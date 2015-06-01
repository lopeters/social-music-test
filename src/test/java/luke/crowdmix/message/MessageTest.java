package luke.crowdmix.message;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MessageTest {
    @Test
    public void toStringOutputsMessageAndHowLongAgoItWasPosted() {
        Iterator<LocalDateTime> iterator = dateSupplier(LocalDateTime.of(2015, 1, 6, 1, 0), LocalDateTime.of(2015, 1, 6, 1, 5));
        Message underTest = new Message("message", iterator::next);
        assertThat(underTest.toString(), is("message (5 minutes ago)"));
    }

    private Iterator<LocalDateTime> dateSupplier(LocalDateTime... dates) {
        return asList(dates).iterator();
    }

}