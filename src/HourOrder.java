import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class HourOrder {
    public static void main(final String[] args) {
        while(true) {
            final Scanner scanner = new Scanner(System.in);
            System.out.print("輸入時段: ");
            final int[] hours = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::valueOf).toArray();
            System.out.println("hours = " + extendHours(hours));
        }
    }

    public static Stream<Integer> extendHours(final int... hours){
        final List<Integer> extendHours = new LinkedList<>();
        for (final Integer hour : hours){
            extendHours.add(hour);
            extendHours.add(hour + 8);
            extendHours.add(hour + 16);
        }
        return extendHours.stream().distinct().filter(hour -> hour < 24).sorted();
    }
}
