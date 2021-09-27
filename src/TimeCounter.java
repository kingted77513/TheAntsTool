import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class TimeCounter {

    public void run() {
        final Scanner scanner = new Scanner(System.in);

        List<Long> lastTimes = null;

        while(true){
            try {
                System.out.print("輸入等待時間 (dd hh mm 或 hh mm): ");
                List<Long> times = Arrays.stream(scanner.nextLine()
                        .split("\\s+")).filter(array -> !array.isEmpty())
                    .map(Long::valueOf).collect(Collectors.toList());

                if (times.isEmpty() && !Objects.isNull(lastTimes)){
                    times = lastTimes;
                }

                if (times.size() < 2 || times.size() > 3) {
                    break;
                }

                final long seconds = getEvolutionSeconds(times);
                final LocalDateTime simpleFinishTime = LocalDateTime.now().plusSeconds(seconds);
                final LocalDateTime discountFinishTime = LocalDateTime.now().plusSeconds(getDiscountSeconds(seconds));

                System.out.println("單純預計完成時間：" + simpleFinishTime.format(DateTimeFormatter.ofPattern("MM/dd(E) HH:mm")));
                System.out.println("加速預計完成時間：" + discountFinishTime.format(DateTimeFormatter.ofPattern("MM/dd(E) HH:mm")));

                lastTimes = times;
            }catch (final Exception e){
                System.out.println("錯誤訊息 = " + e.getMessage());
            }
        }
    }

    private long getEvolutionSeconds(final List<Long> times){
        final long days = (times.size() == 3) ? times.get(0) : 0;
        final long hours = (times.size() == 3) ? times.get(1) : times.get(0);
        final long minutes = (times.size() == 3) ? times.get(2) : times.get(1);
        return TimeUnit.DAYS.toSeconds(days) + TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes) ;
    }

    private long getDiscountSeconds(final long seconds){
        return (long)(seconds * get進化菌叢DiscountProportion() * get聯盟進化DiscountProportion()) - get聯盟蟻棲息地DiscountSeconds();
    }

    private int get聯盟蟻棲息地DiscountSeconds(){
        final int 幫助次數 = 19;
        final int 幫助減少時間 = 78;
        return 幫助次數 * 幫助減少時間;
    }

    protected abstract double get進化菌叢DiscountProportion();

    protected abstract double get聯盟進化DiscountProportion();
}
