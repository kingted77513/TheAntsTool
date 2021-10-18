import java.time.DayOfWeek;

public class EvolutionTimeCounter extends TimeCounter {
    @Override
    protected double get進化菌叢DiscountProportion() {
        return 0 / 100.0;
    }

    @Override
    protected double get聯盟進化DiscountProportion() {
        return 3 / 100.0;
    }

    @Override
    protected 蟻群活動Week get蟻群活動Week() {
        final 蟻群活動Week week = new 蟻群活動Week();
        week.insert(DayOfWeek.MONDAY, 1, 4, 6, 7);
        week.insert(DayOfWeek.TUESDAY, 5, 6);
        week.insert(DayOfWeek.WEDNESDAY, 1, 3, 6);
        week.insert(DayOfWeek.THURSDAY, 6);
        week.insert(DayOfWeek.FRIDAY, 2, 4, 6);
        week.insert(DayOfWeek.SATURDAY, 1, 4, 5, 7);
        week.insert(DayOfWeek.SUNDAY, 3, 6);
        return week;
    }

    public static void main(final String[] args) {
        System.out.println("進化升級時間計算 (增加戰鬥力超過500)");
        new EvolutionTimeCounter().run();
    }
}
