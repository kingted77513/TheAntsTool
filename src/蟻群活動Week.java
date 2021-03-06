import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class 蟻群活動Week {

    private final ZonedDateTime firstDay;
    private final List<蟻群活動Day> days;

    public 蟻群活動Week() {
        this.firstDay = getFirstDay();
        this.days = new LinkedList<>();
    }

    private ZonedDateTime getFirstDay() {
        final ZoneId utc = ZoneId.of("UTC");
        final ZonedDateTime now = ZonedDateTime.now(utc);
        return ZonedDateTime.of(
            now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate(),
            LocalTime.MIN, utc);
    }

    public void insert(final DayOfWeek dayOfWeek, final int... hours) {
        days.add(new 蟻群活動Day(firstDay.plusDays(dayOfWeek.getValue() - 1), HourOrder.extendHours(hours)));
    }

    public Optional<ZonedDateTime> find(final ZonedDateTime finishTime) {
        return this.days.stream().flatMap(day -> day.getActivityHours().stream())
            .filter(time -> finishTime.isAfter(time) &&
                finishTime.isBefore(time.plusMinutes(蟻群活動Day.get活動結束時間分鐘數())))
            .findFirst();
    }

    public Optional<ZonedDateTime> findNextWeek(final ZonedDateTime finishTime) {
        return this.days.stream().flatMap(day -> day.getActivityHours().stream())
            .map(time -> time.plusWeeks(1))
            .filter(time -> finishTime.isAfter(time) &&
                finishTime.isBefore(time.plusMinutes(蟻群活動Day.get活動結束時間分鐘數())))
            .findFirst();
    }

    public Optional<ZonedDateTime> findNearlyAfter(final ZonedDateTime finishTime) {
        final long bufferMinutes = 5;
        return this.days.stream().flatMap(day -> day.getActivityHours().stream())
            .filter(finishTime::isBefore)
            .map(time -> time.plusMinutes(bufferMinutes))
            .findFirst();
    }

    public Optional<ZonedDateTime> findNearlyBefore(final ZonedDateTime finishTime) {
        return this.days.stream().flatMap(day -> day.getActivityHours().stream())
            .filter(finishTime::isAfter)
            .map(time -> time.plusMinutes(蟻群活動Day.get活動結束時間分鐘數()))
            .max(Comparator.naturalOrder());
    }

    public ZonedDateTime findNearlyAfterNotThisWeek(final ZonedDateTime finishTime) {
        final long bufferMinutes = 5;
        while (true) {
            final Optional<ZonedDateTime> matchTime = this.days.stream().flatMap(day -> day.getActivityHours().stream())
                .map(time -> time.plusWeeks(1))
                .filter(finishTime::isBefore)
                .map(time -> time.plusMinutes(bufferMinutes))
                .findFirst();

            if (matchTime.isPresent()) {
                return matchTime.get();
            }
        }
    }

}
