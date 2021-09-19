import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TimeCounter {
    final int reduceSeconds;

    private TimeCounter(int reduceSeconds) {
        this.reduceSeconds = reduceSeconds;
    }

    private LocalDateTime getFinishTime(List<Long> times) throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (times.size() == 3){
            return plusTimeIncludeDays(localDateTime, times);
        }else if (times.size() == 2){
            return plusTimeInADay(localDateTime, times);
        }else {
            throw new Exception("Time format error");
        }
    }

    private LocalDateTime plusTimeIncludeDays(LocalDateTime now, List<Long> times){
        Long days = times.get(0);
        LocalDateTime finishTime = now.plusDays(days);
        times.remove(days);
        return plusTimeInADay(finishTime, times);
    }

    private LocalDateTime plusTimeInADay(LocalDateTime now, List<Long> times){
        Long hours = times.get(0);
        Long minutes = times.get(1);

        LocalDateTime finishTime = now.plusHours(hours);
        finishTime = finishTime.plusMinutes(minutes);
        finishTime = finishTime.plusSeconds(this.reduceSeconds);
        return finishTime;
    }

    private enum AllianceAntHabitat{
        L16(75, 18);

        private int helpTimes;
        private int helpSeconds;

        AllianceAntHabitat(int helpSeconds, int helpTimes){
            this.helpSeconds = helpSeconds;
            this.helpTimes = helpTimes;
        }

        int getMaxHelpSeconds(){
            return this.helpSeconds * this.helpTimes;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        final int reduceTime = AllianceAntHabitat.L16.getMaxHelpSeconds();
        TimeCounter timeCounter = new TimeCounter(reduceTime);

        List<Long> lastTimes = null;

        while(true){
            System.out.print("輸入等待時間 (dd hh mm 或 hh mm): ");
            List<Long> times = Arrays.stream(scanner.nextLine()
                    .split("\\s+")).filter(array -> !array.isEmpty())
                .map(Long::valueOf).collect(Collectors.toList());

            if (times.isEmpty() && !Objects.isNull(lastTimes)){
                times = lastTimes;
            }

            if (times.size() < 2 || times.size() > 3) break;

            LocalDateTime finishTime = timeCounter.getFinishTime(times);
            System.out.println("finish time: " + finishTime.format(DateTimeFormatter.ofPattern("MM/dd(E) HH:mm")));

            lastTimes = times;
        }
    }
}
