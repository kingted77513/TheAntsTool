import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TimeCounter {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        TimeCounter timeCounter = new TimeCounter();

        while(true){
            System.out.print("輸入等待時間 (dd hh mm 或 hh mm): ");
            List<Long> times = Arrays.stream(scanner.nextLine()
                .split("\\s+")).map(Long::valueOf).collect(Collectors.toList());

            if (times.size() < 2 || times.size() > 3) break;

            LocalDateTime finishTime = timeCounter.getFinishTime(times);
            System.out.println("finish time: " + finishTime.format(DateTimeFormatter.ofPattern("MM/dd(E) HH:mm")));
        }
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
        return finishTime;
    }
}
