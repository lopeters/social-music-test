package luke.crowdmix.util;

import java.time.Duration;
import java.time.temporal.Temporal;

public class DateUtil {
    public static String describeDurationBetween(Temporal start, Temporal end) {
        Duration duration = Duration.between(start, end);
        if (duration.toDays() > 1) {
            return duration.toDays() + " days";
        }
        if (duration.toDays() == 1) {
            return duration.toDays() + " day";
        }
        if (duration.toHours() > 1) {
            return duration.toHours() + " hours";
        }
        if (duration.toHours() == 1) {
            return duration.toHours() + " hour";
        }
        if (duration.toMinutes() > 1) {
            return duration.toMinutes() + " minutes";
        }
        if (duration.toMinutes() == 1) {
            return duration.toMinutes() + " minute";
        }
        if (duration.getSeconds() == 1) {
            return duration.getSeconds() + " second";
        }
        return duration.getSeconds() + " seconds";
    }
}
