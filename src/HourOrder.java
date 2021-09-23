import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HourOrder {
    public static void main(String[] args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("輸入時段: ");
            List<Integer> hours = Arrays.stream(scanner.nextLine().split("\\s+"))
                .map(Integer::valueOf).collect(Collectors.toList());
            System.out.println("hours = " + extendHours(hours));
        }
    }

    private static List<Integer> extendHours(List<Integer> hours){
        List<Integer> extendHours = new LinkedList<>();
        for (Integer hour : hours){
            extendHours.add(hour);
            extendHours.add(hour + 8);
            extendHours.add(hour + 16);
        }
        return extendHours.stream().distinct().sorted().collect(Collectors.toList());
    }
}
