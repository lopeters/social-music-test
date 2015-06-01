package luke.crowdmix.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static luke.crowdmix.util.DateUtil.describeDuration;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateUtilTest {

    @Test
    public void describeDurationInDays() {
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 29, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 day"));
        assertThat(describeDuration(LocalDateTime.of(2014, 1, 30, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("365 days"));
    }

    @Test
    public void describeDurationInHours() {
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 11, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 hour"));
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 29, 13, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("23 hours"));
    }

    @Test
    public void describeDurationInMinutes() {
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 11, 59), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 minute"));
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 11, 01), LocalDateTime.of(2015, 1, 30, 12, 00)), is("59 minutes"));
    }

    @Test
    public void describeDurationInSeconds() {
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 11, 59, 59), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 second"));
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 11, 59, 01), LocalDateTime.of(2015, 1, 30, 12, 00)), is("59 seconds"));
        assertThat(describeDuration(LocalDateTime.of(2015, 1, 30, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("0 seconds"));
    }

}