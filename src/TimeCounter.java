import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class TimeCounter {

    public void run() {
        final Scanner scanner = new Scanner(System.in);

        List<Long> lastTimes = null;

        while (true) {
            try {
                System.out.println("-----------------------------------");
                System.out.print("輸入等待時間 (dd hh mm 或 hh mm): ");
                List<Long> times = Arrays.stream(scanner.nextLine()
                        .split("\\s+")).filter(array -> !array.isEmpty())
                    .map(Long::valueOf).collect(Collectors.toList());

                if (times.isEmpty() && !Objects.isNull(lastTimes)) {
                    times = lastTimes;
                }

                if (times.size() < 2 || times.size() > 3) {
                    System.out.println("參數錯誤，結束！");
                    break;
                }

                final long seconds = getEvolutionSeconds(times);
                final ZonedDateTime simpleFinishTime = ZonedDateTime.now().plusSeconds(seconds);
                final ZonedDateTime discountFinishTime = ZonedDateTime.now().plusSeconds(getDiscountSeconds(seconds));

                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd(E) HH:mm");

                System.out.println("1. 台灣時間");
                System.out.println("1-1. 單純預計完成時間：" + simpleFinishTime.format(formatter));
                System.out.println("1-2. 加速預計完成時間：" + discountFinishTime.format(formatter));

                System.out.println("2. UTC時間");
                final 蟻群活動Week week = get蟻群活動Week();
                final ZoneId utcZoneId = ZoneId.of("UTC");
                檢視加速前活動配合時間(week, formatter, simpleFinishTime.withZoneSameInstant(utcZoneId));
                檢視加速後活動配合時間(week, formatter, discountFinishTime.withZoneSameInstant(utcZoneId));

                lastTimes = times;
            } catch (final Exception e) {
                System.out.println("錯誤訊息 = " + e.getMessage());
            }
        }
    }

    private void 檢視加速前活動配合時間(final 蟻群活動Week week, final DateTimeFormatter formatter, final ZonedDateTime simpleFinishTimeUtc) {
        System.out.println("2-1. 單純預計完成時間(UTC)：" + simpleFinishTimeUtc.format(formatter));
        final Optional<ZonedDateTime> matchTime = week.find(simpleFinishTimeUtc);
        if (matchTime.isPresent()) {
            System.out.println("中了！活動配合時間(UTC)：" + matchTime.get().format(formatter));
        } else {
            System.out.println("沒中！請進行加速調整！");
            檢視最近之前時間(week, formatter, simpleFinishTimeUtc);
        }
    }

    private void 檢視加速後活動配合時間(final 蟻群活動Week week, final DateTimeFormatter formatter, final ZonedDateTime discountFinishTimeUtc) {
        System.out.println("2-2. 加速預計完成時間(UTC)：" + discountFinishTimeUtc.format(formatter));

        final Optional<ZonedDateTime> matchTime = week.find(discountFinishTimeUtc);
        if (matchTime.isPresent()) {
            System.out.println("中了！活動配合時間(UTC)：" + matchTime.get().format(formatter));
        } else {
            檢視最近之前時間(week, formatter, discountFinishTimeUtc);
            檢視最近之後時間(week, formatter, discountFinishTimeUtc);
        }
    }

    private void 檢視最近之後時間(final 蟻群活動Week week, final DateTimeFormatter formatter, final ZonedDateTime discountFinishTimeUtc) {
        final Optional<ZonedDateTime> nearlyMatchTimeAfterOptional = week.findNearlyAfter(discountFinishTimeUtc);
        if (nearlyMatchTimeAfterOptional.isPresent()) {
            final ZonedDateTime nearlyMatchTime = nearlyMatchTimeAfterOptional.get();

            System.out.println("沒中！最接近之後時間(UTC)：" + nearlyMatchTime.format(formatter));
            printDiffTime(LocalDateTime.MIN.plusSeconds(Duration.between(discountFinishTimeUtc, nearlyMatchTime).getSeconds()));
        } else {
            System.out.println("這星期沒有之後的配合時間");
        }
    }

    private void printDiffTime(final LocalDateTime diffTime) {
        final String day = (diffTime.getDayOfYear() > 1) ? Integer.toString(diffTime.getDayOfYear() - 1) : "";
        System.out.printf("差距時間：%s %s%n", day, diffTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void 檢視最近之前時間(final 蟻群活動Week week, final DateTimeFormatter formatter, final ZonedDateTime discountFinishTimeUtc) {
        final Optional<ZonedDateTime> nearlyMatchTimeBeforeOptional = week.findNearlyBefore(discountFinishTimeUtc);
        if (nearlyMatchTimeBeforeOptional.isPresent()) {
            final ZonedDateTime nearlyMatchTime = nearlyMatchTimeBeforeOptional.get();

            System.out.println("沒中！最接近之前時間(UTC)：" + nearlyMatchTime.format(formatter));
            printDiffTime(LocalDateTime.MIN.plusSeconds(Duration.between(nearlyMatchTime, discountFinishTimeUtc).getSeconds()));
        } else {
            System.out.println("這星期沒有之前的配合時間");
        }
    }

    private long getEvolutionSeconds(final List<Long> times) {
        final long days = (times.size() == 3) ? times.get(0) : 0;
        final long hours = (times.size() == 3) ? times.get(1) : times.get(0);
        final long minutes = (times.size() == 3) ? times.get(2) : times.get(1);
        return TimeUnit.DAYS.toSeconds(days) + TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes);
    }

    private long getDiscountSeconds(final long seconds) {
        return (long) (seconds - (get聯盟蟻棲息地DiscountSeconds() * (1 + get進化菌叢DiscountProportion() + get聯盟進化DiscountProportion())));
    }

    private int get聯盟蟻棲息地DiscountSeconds() {
        final int 幫助次數 = 19;
        final int 幫助減少時間 = 78;
        return 幫助次數 * 幫助減少時間;
    }

    protected abstract double get進化菌叢DiscountProportion();

    protected abstract double get聯盟進化DiscountProportion();

    protected abstract 蟻群活動Week get蟻群活動Week();
}
