import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class 蟻群活動Day {

    private final List<ZonedDateTime> activityHours;

    public 蟻群活動Day(final ZonedDateTime day, final Stream<Integer> hours) {
        this.activityHours = hours.map(day::plusHours).map(hour -> hour.plusMinutes(5))
            .collect(Collectors.toList());
    }

    public List<ZonedDateTime> getActivityHours() {
        return this.activityHours;
    }
}
