import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class 蟻群活動Day {

    private final List<ZonedDateTime> activityHours;

    public 蟻群活動Day(final ZonedDateTime day, final Stream<Integer> hours) {
        this.activityHours = hours.map(day::plusHours).map(hour -> hour.plusMinutes(get活動開始分鐘數()))
            .collect(Collectors.toList());
    }

    public List<ZonedDateTime> getActivityHours() {
        return this.activityHours;
    }

    private static int get活動開始分鐘數() {
        return 5;
    }

    public static int get活動結束時間分鐘數() {
        final int bufferMinutes = 1;
        return (int) TimeUnit.HOURS.toMinutes(1) - get活動開始分鐘數() - bufferMinutes;
    }
}
