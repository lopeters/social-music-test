package luke.crowdmix.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static luke.crowdmix.util.DateUtil.describeDurationBetween;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateUtilTest {

    @Test
    public void describeDurationInDays() {
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 29, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 day"));
        assertThat(describeDurationBetween(LocalDateTime.of(2014, 1, 30, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("365 days"));
    }

    @Test
    public void describeDurationInHours() {
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 11, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 hour"));
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 29, 13, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("23 hours"));
    }

    @Test
    public void describeDurationInMinutes() {
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 11, 59), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 minute"));
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 11, 01), LocalDateTime.of(2015, 1, 30, 12, 00)), is("59 minutes"));
    }

    @Test
    public void describeDurationInSeconds() {
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 11, 59, 59), LocalDateTime.of(2015, 1, 30, 12, 00)), is("1 second"));
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 11, 59, 01), LocalDateTime.of(2015, 1, 30, 12, 00)), is("59 seconds"));
        assertThat(describeDurationBetween(LocalDateTime.of(2015, 1, 30, 12, 00), LocalDateTime.of(2015, 1, 30, 12, 00)), is("0 seconds"));
    }

}