public class BuildingTimeCounter extends TimeCounter {
    @Override
    protected double get進化菌叢DiscountProportion() {
        final double 加速比例 = 10 / 100.0;
        return (1 - 加速比例);
    }

    @Override
    protected double get聯盟進化DiscountProportion() {
        final double 加速比例 = 0 / 100.0;
        return (1 - 加速比例);
    }

    public static void main(final String[] args) {
        System.out.println("建築升級時間計算");
        new BuildingTimeCounter().run();
    }
}
