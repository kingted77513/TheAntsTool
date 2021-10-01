import java.time.DayOfWeek;

public class BuildingTimeCounter extends TimeCounter {
    @Override
    protected double get進化菌叢DiscountProportion() {
        return 10 / 100.0;
    }

    @Override
    protected double get聯盟進化DiscountProportion() {
        return 5 / 100.0;
    }

    @Override
    protected 蟻群活動Week get蟻群活動Week() {
        final 蟻群活動Week week = new 蟻群活動Week();
        week.insert(DayOfWeek.FRIDAY, 2, 4);
        week.insert(DayOfWeek.SATURDAY, 2, 4, 5, 6);
        week.insert(DayOfWeek.SUNDAY, 0, 6);
        return week;
    }

    public static void main(final String[] args) {
        System.out.println("建築升級時間計算");
        new BuildingTimeCounter().run();
    }
}
