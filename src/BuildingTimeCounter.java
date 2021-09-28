public class BuildingTimeCounter extends TimeCounter {
    @Override
    protected double get進化菌叢DiscountProportion() {
        return 10 / 100.0;
    }

    @Override
    protected double get聯盟進化DiscountProportion() {
        return 5 / 100.0;
    }

    public static void main(final String[] args) {
        System.out.println("建築升級時間計算");
        new BuildingTimeCounter().run();
    }
}
